package net.iesochoa.jesusayala.practica5.model.temp
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.iesochoa.jesusayala.practica5.model.Tarea
import kotlin.random.Random

object ModelTempTareas {

    //lista de tareas
    var tareas = ArrayList<Tarea>()
    //LiveData para observar en la vista los cambios en la lista
    val tareasLiveData = MutableLiveData<List<Tarea>>(tareas)
    //el context que suele ser necesario en acceso a datos
    private lateinit var application: Application

    //Permite iniciar el objeto Singleton
    operator fun invoke(context: Context){
        this.application= context.applicationContext as Application
        iniciaPruebaTareas()
    }

    fun getAllTareas(): LiveData<List<Tarea>> {
        return tareasLiveData
    }

    fun iniciaPruebaTareas() {
        val tecnicos = listOf(
            "Pepe Gotero",
            "Sacarino Pómez",
            "Mortadelo Fernández",
            "Filemón López",
            "Zipi Climent",
            "Zape Gómez"
        )
        lateinit var tarea: Tarea
        (1..10).forEach {
            tarea = Tarea(
                (0..4).random(),
                (0..2).random(),
                Random.nextBoolean(),
                (0..2).random(),
                (0..30).random(),
                (0..5).random().toFloat(),
                tecnicos.random(),
                "Lorem ipsum dolor sit amet, " +
                        "consectetur adipiscing elit. Mauris consequat ligula et vehicula" +
                        " mattis. Etiam tristique ornare lacinia. Vestibulum lacus magna," +
                        " dignissim et tempor id, convallis sed augue"
            )
            tareas.add(tarea)
        }
        //actualizamos el LiveData
        tareasLiveData.value = tareas
    }

    fun borrarTarea(tarea: Tarea) {
        tareas.remove(tarea)
        tareasLiveData.value = tareas
    }

    fun modificarTarea(tareaAnterior: Tarea, tareaNueva: Tarea) {
        // Buscamos la tarea anterior en la lista de tareas
        val posicion = tareas.indexOf(tareaAnterior)
        // Reemplazamos la tarea anterior por la nueva
        tareas[posicion] = tareaNueva
        //actualizamos el LiveData
        tareasLiveData.value = tareas
    }
}