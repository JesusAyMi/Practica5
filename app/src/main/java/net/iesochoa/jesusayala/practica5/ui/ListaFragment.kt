package net.iesochoa.jesusayala.practica5.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.iesochoa.jesusayala.practica5.R
import net.iesochoa.jesusayala.practica5.databinding.FragmentListaBinding
import net.iesochoa.jesusayala.practica5.databinding.ItemTareaBinding
import net.iesochoa.jesusayala.practica5.model.Tarea
import net.iesochoa.jesusayala.practica5.model.viewModel.ViewModels
import net.iesochoa.jesusayala.practica5.ui.adapters.TareasAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListaFragment : Fragment() {

    private var _binding: FragmentListaBinding? = null
    private val viewModel: ViewModels by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var tareasAdapter: TareasAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciaRecyclerView()
        iniciaFiltros()

        viewModel.tareasLiveData.observe(viewLifecycleOwner, Observer<List<Tarea>> { lista ->
            //actualizaLista(lista)
            tareasAdapter.setLista(lista)
        })

        binding.fabNuevo.setOnClickListener {
            val action = ListaFragmentDirections.actionEditar(null)
            findNavController().navigate(action)
        }

        viewModel.tareasLiveData.observe(viewLifecycleOwner, Observer<List<Tarea>> { lista ->
            actualizaLista(lista)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun actualizaLista(lista: List<Tarea>){
        //tvLista.text = ""
        for (tarea in lista) {
            //tvLista.append("Tarea ${tarea.id} realizada por ${tarea.tecnico}\n")
        }
    }

    private fun iniciaFiltros(){
        binding.swSinPagar.setOnCheckedChangeListener( ) { _,isChecked->
            //actualiza el LiveData SoloSinPagarLiveData que a su vez modifica tareasLiveData
            //mediante el Transformation
            viewModel.setSoloSinPagar(isChecked)}
    }


    private fun iniciaRecyclerView() {
        //creamos el adaptador
        tareasAdapter = TareasAdapter()

        with(binding.rvTareas) {
            //Creamos el layoutManager
            layoutManager = LinearLayoutManager(activity)
            //le asignamos el adaptador
            adapter = tareasAdapter
        }
    }
}