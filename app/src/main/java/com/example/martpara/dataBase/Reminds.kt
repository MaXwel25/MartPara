package com.example.martpara.dataBase

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index("id")]
)

data class Reminds(
    @PrimaryKey val id : Int,
    val text : String = "",
    var datetime : Long = 0
)
