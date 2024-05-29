package com.rudachenkoroman.astonIntensivFinal.domain.model.source

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "source"
)

data class Source(
    @PrimaryKey(autoGenerate = true)
    val category: String,
    val country: String,
    val description: String,
    val id: String,
    val language: String,
    val name: String,
    val url: String
) : Serializable