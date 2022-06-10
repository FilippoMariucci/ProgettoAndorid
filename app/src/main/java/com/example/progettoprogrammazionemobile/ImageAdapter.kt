package com.example.progettoprogrammazionemobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.*

class ImageAdapter(private val categories: List<category>):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category,parent,false)
        return ImageViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = categories[position]

        holder.imagesrc.setImageResource(currentItem.imageSrc)
        holder.pipo.text = currentItem.text

    }

    override fun getItemCount(): Int = categories.size

    class ImageViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val imagesrc : ImageView = itemView.findViewById<ImageView>(R.id.image_category)
        val pipo : TextView = itemView.findViewById(R.id.categoryTitle)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}