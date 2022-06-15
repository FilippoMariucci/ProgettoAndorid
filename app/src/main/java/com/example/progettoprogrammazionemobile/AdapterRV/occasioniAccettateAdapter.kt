package com.example.progettoprogrammazionemobile.AdapterRV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.R
import com.example.progettoprogrammazionemobile.model.Evento

class occasioniAccettateAdapter(private val occasioniAccettate: ArrayList<Evento>):
RecyclerView.Adapter<occasioniAccettateAdapter.viewHolder>() {
    private lateinit var aListener : OnEventClickListener

    interface OnEventClickListener{
        fun seeMoreclick(idEvento: String)
        fun cancelclick (idEvento: String)
    }

    fun setOnEventClickListener(listener : OnEventClickListener){
        aListener = listener
    }

    override fun getItemCount(): Int {
        return occasioniAccettate.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentAcceptedEvent = occasioniAccettate[position]
        holder.idEvento = currentAcceptedEvent.id_evento.toString()

        holder.tAccepted.text = currentAcceptedEvent.titolo
        holder.dAccepted.text = currentAcceptedEvent.data_evento
        holder.cAccepted.text = currentAcceptedEvent.citta
        holder.pAccepted.text  = currentAcceptedEvent.costo

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.occasioniaccettate_item, parent, false)
        return viewHolder(itemView, aListener)
    }

    class viewHolder(itemView: View, listener: OnEventClickListener) : RecyclerView.ViewHolder(itemView){
        val tAccepted : TextView = itemView.findViewById(R.id.TitoloEventoAccettato)
        var idEvento : String = ""
        var idPartecipante : String = ""
        val dAccepted : TextView = itemView.findViewById(R.id.dataEventoAccettato)
        val cAccepted : TextView = itemView.findViewById(R.id.cittaEventoAccettato)
        val pAccepted : TextView = itemView.findViewById(R.id.prezzoEventoAccettato)
        val seeMore : Button = itemView.findViewById(R.id.seeMore)
        val cancel : Button = itemView.findViewById(R.id.cancelPartecipazione)

    init {
            seeMore.setOnClickListener{
                listener.seeMoreclick(idEvento as String)
            }
            cancel.setOnClickListener{
                listener.cancelclick(idEvento as String)
            }
        }

    }
}