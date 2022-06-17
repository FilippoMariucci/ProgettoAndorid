package com.example.progettoprogrammazionemobile.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "image_table")

class ImageUrlDb {
    @PrimaryKey @ColumnInfo(name = "url")
    var url: String
        get() {
            return field
        }

        set(value) {
            field = value
        }

    constructor() : this("")
    constructor(url: String){
        this.url = url

    }

}