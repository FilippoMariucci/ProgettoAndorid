package com.example.progettoprogrammazionemobile

import android.content.Context
import android.icu.util.ULocale
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ImageAdapter(
    private val context: Context,
    private val images: List<category>
):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cats = itemView.findViewById<ImageView>(R.id.image_category)
        fun bindView(Category: category) {
            cats.setImageResource(Category.imageSrc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.category, parent, false))


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position])

    }

    override fun getItemCount(): Int = images.size
}