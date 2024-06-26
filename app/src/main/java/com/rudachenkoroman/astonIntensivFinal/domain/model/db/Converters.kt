package com.rudachenkoroman.astonIntensivFinal.domain.model.db

import androidx.room.TypeConverter
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}