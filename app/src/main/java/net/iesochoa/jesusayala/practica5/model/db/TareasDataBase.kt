package net.iesochoa.jesusayala.practica5.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.iesochoa.jesusayala.practica5.model.Tarea

@Database(entities = arrayOf(Tarea::class), version = 1, exportSchema = false)
public abstract class TareasDataBase: RoomDatabase() {
    abstract fun tareasDao(): TareasDao

    companion object{
        @Volatile
        private var INSTANCE: TareasDataBase? = null
        fun getDatabase(context: Context): TareasDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TareasDataBase::class.java,
                    "tareas_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}