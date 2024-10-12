package com.example.animemangatoon.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animemangatoon.R
import com.example.animemangatoon.modelclass.Webtoon

class WebtoonAdapter(
    private val webtoonList: List<Webtoon>,
    private val onItemClick: (Webtoon) -> Unit // Lambda function for click handling
) : RecyclerView.Adapter<WebtoonAdapter.WebtoonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebtoonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_webtoon, parent, false)
        return WebtoonViewHolder(view)
    }

    override fun onBindViewHolder(holder: WebtoonViewHolder, position: Int) {
        val webtoon = webtoonList[position]
        holder.titleTextView.text = webtoon.title
        holder.descriptionTextView.text = webtoon.description
        Glide.with(holder.itemView.context)
            .load(webtoon.imageUrl)
            .into(holder.imageView)

        // Handle item click using the lambda function
        holder.itemView.setOnClickListener {
            onItemClick(webtoon)
        }
    }

    override fun getItemCount(): Int = webtoonList.size

    class WebtoonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.webtoonTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.webtoonDescription)
        val imageView: ImageView = itemView.findViewById(R.id.webtoonImage)
    }
}
