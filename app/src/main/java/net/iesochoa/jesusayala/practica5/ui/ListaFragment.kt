package net.iesochoa.jesusayala.practica5.ui

import android.annotation.SuppressLint
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
import net.iesochoa.jesusayala.practica5.databinding.FragmentListaBinding
import net.iesochoa.jesusayala.practica5.model.Tarea
import net.iesochoa.jesusayala.practica5.model.viewModel.ViewModels

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListaFragment : Fragment() {

    private var _binding: FragmentListaBinding? = null
    private lateinit var tvLista: TextView
    private val viewModel: ViewModels by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        tvLista = binding.tvLista


        binding.fabNuevo.setOnClickListener {
            val action = ListaFragmentDirections.actionEditar(null)
            findNavController().navigate(action)

            //(requireActivity() as AppCompatActivity).supportActionBar?.title = "Nueva tarea"
        }

        binding.btPruebaEdicion.setOnClickListener{
            val lista = viewModel.tareasLiveData.value
            val tarea = lista?.random()
            val action = ListaFragmentDirections.actionEditar(tarea)
            findNavController().navigate(action)

            if (tarea != null) {
                //(requireActivity() as AppCompatActivity).supportActionBar?.title = "Tarea " + tarea.id
            }
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
        tvLista.text = ""
        for (tarea in lista) {
            tvLista.append("Tarea ${tarea.id} realizada por ${tarea.tecnico}\n")
        }
    }
}