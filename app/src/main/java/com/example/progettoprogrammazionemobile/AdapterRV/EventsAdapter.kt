package com.example.progettoprogrammazionemobile.AdapterRV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.R
import com.example.progettoprogrammazionemobile.homeFragmentDirections
import com.example.progettoprogrammazionemobile.model.Evento

class EventsAdapter (private val eventList: ArrayList<Evento>) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private lateinit var eListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: String)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        eListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(itemView, eListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEvent = eventList[position]
        holder.tvEvent.text = currentEvent.titolo
        holder.idEvent = currentEvent.id_evento.toString()
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvEvent : TextView = itemView.findViewById(R.id.tvEventDesc)
        var idEvent : String = ""

        init {
            itemView.setOnClickListener{
                listener.onItemClick(idEvent as String)
            }
        }
    }

}