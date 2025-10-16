package com.example.apptienda.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Producto::class, Usuario::class, Carrito::class], version = 1)

@TypeConverters(DateConverter::class) // <--- ¡ESTA LÍNEA ES LA SOLUCIÓN!
abstract class AppDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    // abstract fun carritoDao(): CarritoDao // TODO: Agrega el DAO del carrito

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_gamer_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}