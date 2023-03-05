package net.iesochoa.jesusayala.practica5.model.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.iesochoa.jesusayala.practica5.model.Tarea
import net.iesochoa.jesusayala.practica5.repository.Repository

class ViewModels (application: Application) : AndroidViewModel(application) {
    //repositorio
    private val repositorio: Repository
    //liveData de lista de tareas
    val tareasLiveData : LiveData<List<Tarea>>
    //inicio ViewModel
    init {
        //inicia repositorio
        Repository(getApplication<Application>().applicationContext)
        repositorio=Repository
        tareasLiveData = repositorio.getAllTareas()
    }

    fun borrarTarea(tarea: Tarea) = Repository.modelTareas.borrarTarea(tarea)
    fun addTareas(tarea: Tarea) = Repository.modelTareas.iniciaPruebaTareas()
    fun getAllTareas(tarea: Tarea) = Repository.modelTareas.getAllTareas()
}