package com.example.animemangatoon.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animemangatoon.R
import com.example.animemangatoon.modelclass.Webtoon
import com.example.animemangatoon.Adapter.WebtoonAdapter
import com.example.animemangatoon.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {

    lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Custom method to enable edge-to-edge UI
        // Initialize ViewBinding
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val webtoons = listOf(
            Webtoon(1, "Lore Olympus", "A modern retelling of the story of Hades and Persephone.", "https://linktoimage.com/lore_olympus.jpg"),
            Webtoon(2, "Tower of God", "A young man’s journey in a mysterious tower.", "https://linktoimage.com/tower_of_god.jpg"),
            Webtoon(3, "Noblesse", "The story of a noble vampire awakening.", "https://linktoimage.com/noblesse.jpg")
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val webtoonAdapter = WebtoonAdapter(webtoons) { webtoon ->
            val intent = Intent(this, DetailScreen::class.java).apply {
                putExtra("WEBTOON_ID", webtoon.id)
                putExtra("WEBTOON_TITLE", webtoon.title)
                putExtra("WEBTOON_DESCRIPTION", webtoon.description)
                putExtra("WEBTOON_IMAGE_URL", webtoon.imageUrl)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = webtoonAdapter
        binding.button.setOnClickListener {
            val intent = Intent(this, FavouritesScreen::class.java)
            startActivity(intent)
        }


    }

    private fun enableEdgeToEdge() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }
}
