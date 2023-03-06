package net.iesochoa.jesusayala.practica5.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import net.iesochoa.jesusayala.practica5.model.Tarea

@Dao
interface TareasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTarea(tarea: Tarea)
    @Delete
    suspend fun delTarea(tarea: Tarea)
    @Update
    suspend fun updateTarea(tarea: Tarea)

    @Query("SELECT * FROM tareas WHERE pagado= :soloSinPagar")
    fun getTareasFiltroSinPagar(soloSinPagar:Boolean): LiveData<List<Tarea>>

    @Query("SELECT * FROM tareas")
     fun getAllTareas(): List<Tarea>

    @Query("SELECT COUNT(*) FROM tareas")
    suspend fun getNumTareas(): Long

}