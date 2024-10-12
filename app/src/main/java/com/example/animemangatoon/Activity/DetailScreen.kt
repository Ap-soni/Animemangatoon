package com.example.animemangatoon.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.animemangatoon.R
import com.example.animemangatoon.modelclass.Webtoon
import com.example.animemangatoon.DatabaseUtils.WebtoonDatabase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailScreen : AppCompatActivity() {

    private lateinit var webtoonImage: ImageView
    private lateinit var webtoonDescription: TextView
    private lateinit var addToFavoritesButton: Button
    private lateinit var ratingBar: RatingBar
    private lateinit var averageRatingText: TextView
    private lateinit var back: ImageView
    private lateinit var title: TextView

    private var currentRating: Float = 0f
    private var ratingCount: Int = 0

    private var webtoonId: Int = 0 // Store the ID of the webtoon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)

        // Initialize views
        webtoonImage = findViewById(R.id.webtoonImage)
        webtoonDescription = findViewById(R.id.webtoonDescription)
        addToFavoritesButton = findViewById(R.id.btnAddToFavorites)
        ratingBar = findViewById(R.id.ratingBar)
        averageRatingText = findViewById(R.id.averageRatingText)
        back = findViewById(R.id.imgBackk)
        title = findViewById(R.id.txttitle)
        back.visibility= View.VISIBLE
        title.setText(getString(R.string.Detail))


        // Get webtoon details from intent
        val webtoonTitle = intent.getStringExtra("WEBTOON_TITLE")
        val webtoonDesc = intent.getStringExtra("WEBTOON_DESCRIPTION")
        val webtoonImageUrl = intent.getStringExtra("WEBTOON_IMAGE_URL")
        webtoonId = intent.getIntExtra("WEBTOON_ID", 0) // Retrieve the webtoon ID

        // Load image using Glide
        Glide.with(this)
            .load(webtoonImageUrl)
            .into(webtoonImage)

        // Set description
        webtoonDescription.text = webtoonDesc

        // Handle "Add to Favorites" button click
        addToFavoritesButton.setOnClickListener {
            addToFavorites(webtoonTitle)
        }
        back.setOnClickListener{
            onBackPressed()
        }
        // Handle rating changes
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            updateAverageRating(rating)
        }
    }

    private fun addToFavorites(webtoonTitle: String?) {
        // Add to Room Database
        if (webtoonTitle != null) {
            val webtoon = Webtoon(webtoonId, webtoonTitle, webtoonDescription.text.toString(), webtoonImage.toString())

            CoroutineScope(Dispatchers.IO).launch {
                val db = WebtoonDatabase.getDatabase(this@DetailScreen)
                db.webtoonDao().insert(webtoon)

                runOnUiThread {
                    Toast.makeText(this@DetailScreen, "$webtoonTitle added to favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateAverageRating(newRating: Float) {
        // Update rating calculation
        currentRating = ((currentRating * ratingCount) + newRating) / (ratingCount + 1)
        ratingCount++

        // Update the average rating display
        averageRatingText.text = "Average Rating: %.2f".format(currentRating)
    }
}
