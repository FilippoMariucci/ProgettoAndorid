package com.example.progettoprogrammazionemobile.AdapterRV

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.decode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.R
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineStart
import java.lang.Integer.decode
import kotlin.collections.ArrayList


class EventsAdapter (private val eventList: ArrayList<Evento>, private val mapBitEvents: Map<String,Bitmap>) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private lateinit var eListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: String)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        eListener = listener
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        val imageBytes = Base64.decode(encodedString, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(itemView, eListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEvent = eventList[position]
        holder.tvEvent.text = currentEvent.titolo
        holder.idEvent = currentEvent.id_evento.toString()
        holder.image.setImageBitmap(mapBitEvents[holder.idEvent])
//        holder.image.setImageBitmap(StringToBitMap(currentEvent.foto)?.let {
//            Bitmap.createScaledBitmap(
//                it, 120, 120, false)
//        })
        holder.prezzo.text = currentEvent.costo
        holder.dataEv.text = currentEvent.data_evento
        holder.categorieEv.text = currentEvent.categoria
        holder.descrizioneEv.text = currentEvent.descrizione
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvEvent : TextView = itemView.findViewById(R.id.tvEventDesc)
        var idEvent : String = ""
        val image : ImageView = itemView.findViewById(R.id.immagineEvento)
        val prezzo : TextView = itemView.findViewById(R.id.prezzoEvento)
        val dataEv : TextView = itemView.findViewById(R.id.dataEvento)
        val categorieEv : TextView = itemView.findViewById(R.id.categoriaEvento)
        val descrizioneEv : TextView = itemView.findViewById(R.id.descrizioneItemEvento)
        val bottonInfo : FloatingActionButton = itemView.findViewById(R.id.buttonIminterest)

        init {
            bottonInfo.setOnClickListener{
                listener.onItemClick(idEvent as String)
            }
        }
    }

}