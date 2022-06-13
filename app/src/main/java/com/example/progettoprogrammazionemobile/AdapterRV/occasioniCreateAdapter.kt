package com.example.progettoprogrammazionemobile.AdapterRV
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.R
import com.example.progettoprogrammazionemobile.model.Evento

class occasioniCreateAdapter(private val occasioniUtente: ArrayList<Evento>):
    RecyclerView.Adapter<occasioniCreateAdapter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.occasioni_create_card, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentEvent = occasioniUtente[position]
        holder.iCreated.setImageResource(R.drawable.rico)
        holder.tCreated.text = currentEvent.titolo
        holder.cCreated.text = currentEvent.categoria
        holder.dCreated.text = currentEvent.data_evento
    }

    override fun getItemCount(): Int {
            return  occasioniUtente.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val iCreated : ImageView = itemView.findViewById(R.id.fotoEventoUtente)
        val tCreated : TextView = itemView.findViewById(R.id.titoloEventoCreato)
        val dCreated : TextView = itemView.findViewById(R.id.dataEventoCreato)
        val cCreated : TextView = itemView.findViewById(R.id.categoriaEventoCreato)
    }

}