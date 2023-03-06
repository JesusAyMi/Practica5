package net.iesochoa.jesusayala.practica5.repository
import android.app.Application
import android.content.Context
import net.iesochoa.jesusayala.practica5.model.db.TareasDao
import net.iesochoa.jesusayala.practica5.model.db.TareasDataBase
import net.iesochoa.jesusayala.practica5.model.Tarea

object Repository {

    //instancia al modelo
    //lateinit var modelTareas: ModelTempTareas
    lateinit var modelTareas: TareasDao
    //el context suele ser necesario para recuperar datos
    private lateinit var application: Application
    //inicio del objeto singleton
    operator fun invoke(context: Context){
        this.application= context.applicationContext as Application
        //iniciamos el modelo
        //ModelTempTareas(application)
        modelTareas= TareasDataBase.getDatabase(application).tareasDao()
    }

    suspend fun borrarTarea(tarea: Tarea) = modelTareas.delTarea(tarea)
    suspend fun addTareas(tarea: Tarea) = modelTareas.addTarea(tarea)
    suspend fun getAllTareas() = modelTareas.getAllTareas()
    suspend fun updateTareas(tarea: Tarea) = modelTareas.updateTarea(tarea)
    fun getTareasFiltroSinPagar (soloSinPagar:Boolean) = modelTareas.getTareasFiltroSinPagar(soloSinPagar)
    suspend fun getNumTareas() = modelTareas.getNumTareas()
}