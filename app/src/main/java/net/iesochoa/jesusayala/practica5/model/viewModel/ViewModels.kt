package net.iesochoa.jesusayala.practica5.model.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import net.iesochoa.jesusayala.practica5.model.Tarea
import net.iesochoa.jesusayala.practica5.repository.Repository

class ViewModels (application: Application) : AndroidViewModel(application) {
    //repositorio
    private val repositorio: Repository
    //liveData de lista de tareas
    val tareasLiveData : LiveData<List<Tarea>>
    private val soloSinPagarLiveData = MutableLiveData<Boolean>(false)

    //inicio ViewModel
    init {
        //inicia repositorio
        Repository(getApplication<Application>().applicationContext)
        repositorio=Repository
        tareasLiveData=Transformations.switchMap(soloSinPagarLiveData)
        {soloSinPagar->Repository.getTareasFiltroSinPagar(soloSinPagar)}
    }

    suspend fun borrarTarea(tarea: Tarea) = Repository.borrarTarea(tarea)
    suspend fun addTareas(tarea: Tarea) = Repository.addTareas(tarea)
    fun getAllTareas(tarea: Tarea) = Repository.getAllTareas()

    //creamos el LiveData de tipo Booleano. Representa nuestro filtro
    fun setSoloSinPagar(soloSinPagar:Boolean){soloSinPagarLiveData.value=soloSinPagar}
}