package com.example.progettoprogrammazionemobile.model

class Evento {
    var id_evento: String ?=null
    var titolo: String ?=null
    var descrizione: String ?=null
    var lingue: String ?=null
    var categoria: String ?=null
    var citta: String ?=null
    var indirizzo: String ?=null
    var data_evento: String ?=null
    var costo: String ?=null
    var n_persone: String ?=null
    var foto: String ?=null

    constructor(titolo:String, descrizione:String,lingue:String,
                categoria:String,citta:String, indirizzo:String,
                data_evento:String, costo:String,npersone:String,
                foto:String,){
        this.titolo = titolo
        this.descrizione = descrizione
        this.lingue = lingue
        this.categoria = categoria
        this.citta = citta
        this.indirizzo = indirizzo
        this.data_evento = data_evento
        this.costo = costo
        this.n_persone = n_persone
        this.foto = foto

    }


}
