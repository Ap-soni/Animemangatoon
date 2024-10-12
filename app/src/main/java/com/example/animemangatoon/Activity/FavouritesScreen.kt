package com.example.animemangatoon.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animemangatoon.R
import com.example.animemangatoon.Adapter.WebtoonAdapter
import com.example.animemangatoon.DatabaseUtils.WebtoonDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var webtoonAdapter: WebtoonAdapter
    private lateinit var noFavoritesText: View
    private lateinit var back: ImageView
    private lateinit var title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites_screen)
        back = findViewById(R.id.imgBackk)
        title = findViewById(R.id.txttitle)
        title.setText(getString(R.string.Favourites))
        back.visibility= View.VISIBLE

        // Initialize RecyclerView and no favorites TextView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        noFavoritesText = findViewById(R.id.noFavoritesText)

        // Set up the adapter with an empty list initially
        webtoonAdapter = WebtoonAdapter(listOf()) { webtoon ->
            // Handle item click, navigate to DetailActivity
            val intent = Intent(this, DetailScreen::class.java).apply {
                putExtra("WEBTOON_ID", webtoon.id)
                putExtra("WEBTOON_TITLE", webtoon.title)
                putExtra("WEBTOON_DESCRIPTION", webtoon.description)
                putExtra("WEBTOON_IMAGE_URL", webtoon.imageUrl)
            }
            startActivity(intent)
        }
        back.setOnClickListener{
            onBackPressed()
        }
        recyclerView.adapter = webtoonAdapter

        // Load all webtoons from the database
        loadAllWebtoons()
    }

    private fun loadAllWebtoons() {
        val db = WebtoonDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            // Fetch all webtoons from the database
            val allWebtoons = db.webtoonDao().getFavorites()

            // Debugging output
            Log.d("FavouritesScreen", "Fetched webtoons: ${allWebtoons.size}")

            // Update the UI on the main thread
            runOnUiThread {
                if (allWebtoons.isEmpty()) {
                    // Show "No webtoons found" text and hide RecyclerView
                    noFavoritesText.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    // Hide "No webtoons" text and show RecyclerView
                    noFavoritesText.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    // Update the adapter with the fetched list
                    webtoonAdapter = WebtoonAdapter(allWebtoons) { webtoon ->
                        // Handle item click, navigate to DetailActivity
                        val intent = Intent(this@FavouritesScreen, DetailScreen::class.java).apply {
                            putExtra("WEBTOON_ID", webtoon.id)
                            putExtra("WEBTOON_TITLE", webtoon.title)
                            putExtra("WEBTOON_DESCRIPTION", webtoon.description)
                            putExtra("WEBTOON_IMAGE_URL", webtoon.imageUrl)
                        }
                        startActivity(intent)
                    }
                    recyclerView.adapter = webtoonAdapter
                }
            }
        }
    }
}
