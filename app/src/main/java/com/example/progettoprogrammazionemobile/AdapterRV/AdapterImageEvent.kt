package com.example.progettoprogrammazionemobile.AdapterRV

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.progettoprogrammazionemobile.R
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.event_item.view.*

class AdapterImageEvent (
    val urls: List<String>,
    val eventList: ArrayList<Evento>
    ): RecyclerView.Adapter<AdapterImageEvent.ImageViewHoldertwo>(){

    private lateinit var eListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(idEvento: String, url_image: String)
        fun skipEvent (posizione: String, sizeList: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        eListener = listener
    }

    inner class ImageViewHoldertwo(itemView: View, listener: AdapterImageEvent.onItemClickListener): RecyclerView.ViewHolder(itemView) {
        var idEvent : String = ""
        val bottonInfo : FloatingActionButton = itemView.findViewById(R.id.buttonIminterest)
        val bottonDelete : FloatingActionButton = itemView.findViewById(R.id.buttonNoInterest)
        init {
            bottonInfo.setOnClickListener{
                listener.onItemClick(idEvent as String, urls[adapterPosition])
            }
            bottonDelete.setOnClickListener{
                listener.skipEvent(adapterPosition.toString() as String, eventList.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHoldertwo {
        return ImageViewHoldertwo(
            LayoutInflater.from(parent.context).inflate(
                R.layout.event_item,
                parent,
                false
            ), eListener
        )
    }

    override fun onBindViewHolder(holder: ImageViewHoldertwo, position: Int) {
        val url = urls[position]
        Glide.with(holder.itemView).load(url).into(holder.itemView.immagineEvento)
        val currentEvent = eventList[position]
        Log.d("posizione","$position")
        Log.d("eventlist", "$eventList")
        holder.idEvent = currentEvent.id_evento.toString()
        holder.itemView.tvEventDesc.text = currentEvent.titolo
        holder.itemView.prezzoEvento.text = currentEvent.costo
        holder.itemView.dataEvento.text = currentEvent.data_evento
        holder.itemView.categoriaEvento.text = currentEvent.categoria
    }

    override fun getItemCount(): Int {
        return urls.size
    }


}