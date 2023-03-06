package net.iesochoa.jesusayala.practica5.repository
import android.app.Application
import android.content.Context
import net.iesochoa.jesusayala.practica5.model.Tarea
import net.iesochoa.jesusayala.practica5.model.temp.ModelTempTareas

object Repository {

    //instancia al modelo
    lateinit var modelTareas: ModelTempTareas
    //el context suele ser necesario para recuperar datos
    private lateinit var application: Application
    //inicio del objeto singleton
    operator fun invoke(context: Context){
        this.application= context.applicationContext as
                Application
        //iniciamos el modelo
        ModelTempTareas(application)
        modelTareas=ModelTempTareas
    }

    suspend fun borrarTarea(tarea: Tarea) = modelTareas.borrarTarea(tarea)
    fun addTareas(tarea: Tarea) = modelTareas.iniciaPruebaTareas()
    fun getAllTareas() = modelTareas.getAllTareas()
    fun getTareasFiltroSinPagar (soloSinPagar:Boolean) = modelTareas.getTareasFiltroSinPagar(soloSinPagar)

}