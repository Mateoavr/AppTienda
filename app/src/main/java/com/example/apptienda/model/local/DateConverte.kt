package com.example.apptienda.model.local

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        // Convierte un número (Long) a un objeto Date
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        // Convierte un objeto Date a un número (Long)
        return date?.time
    }
}