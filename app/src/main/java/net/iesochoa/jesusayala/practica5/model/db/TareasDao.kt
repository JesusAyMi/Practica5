package net.iesochoa.jesusayala.practica5.model.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.iesochoa.jesusayala.practica5.model.Tarea

interface TareasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTarea(tarea: Tarea)
    @Delete
    suspend fun delTarea(tarea: Tarea)

    @Query("SELECT * FROM tareas WHERE pagado= :soloSinPagar")
    fun getTareasFiltroSinPagar(soloSinPagar:Boolean): LiveData<List<Tarea>>

}