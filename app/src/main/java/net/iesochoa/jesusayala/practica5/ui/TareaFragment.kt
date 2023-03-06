package net.iesochoa.jesusayala.practica5.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import net.iesochoa.jesusayala.practica5.R
import net.iesochoa.jesusayala.practica5.databinding.FragmentTareaBinding
import net.iesochoa.jesusayala.practica5.model.Tarea
import net.iesochoa.jesusayala.practica5.model.temp.ModelTempTareas
import net.iesochoa.jesusayala.practica5.model.temp.ModelTempTareas.borrarTarea
import net.iesochoa.jesusayala.practica5.model.temp.ModelTempTareas.modificarTarea

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TareaFragment : Fragment() {

    private var _binding: FragmentTareaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: TareaFragmentArgs by navArgs()
    private val viewModel: ViewModel by activityViewModels()
    //será una tarea nueva si no hay argumento
    val esNuevo by lazy { args.tarea==null }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTareaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciaSpCategoria()
        iniciaSpPrioridad()
        iniciaSwPagado()
        iniciaRgEstado()
        iniciaSbHoras()

        binding.fabSave.setOnClickListener {
            comprobarDatosYGuardar()
        }

        //si es nueva tarea o es una edicion
        if (esNuevo)//nueva tarea
            //cambiamos el título de la ventana
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Nueva Tarea"
        else
            iniciaTarea(args.tarea!!)

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }

    private fun iniciaTarea(tarea: Tarea) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Tarea " + tarea.id

        var categoria = binding.spCategoria
        categoria.setSelection(tarea.categoria)

        var prioridad = binding.spPrioridad
        prioridad.setSelection(tarea.prioridad)

        var pagado = binding.swPagado
        pagado.isChecked = tarea.pagado

        var abierto = binding.rbAbierta
        var enCurso = binding.rbEnCurso
        var cerrado = binding.rbCerrada
        when (tarea.estado) {
            0 -> abierto.isChecked
            1 -> enCurso.isChecked
            2 -> cerrado.isChecked
        }

        var horas = binding.sbHorasTrabajadas
        horas.setProgress(tarea.horas)

        var valoracion = binding.rbValoracion
        valoracion.setRating(tarea.valoracion)

        var tecnico = binding.etTecnico
        tecnico.setText(tarea.tecnico)

        var descripcion = binding.etDescripcion
        descripcion.setText(tarea.descripcion)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun iniciaSpCategoria() {
        ArrayAdapter.createFromResource(
            //contexto suele ser la Activity
            requireContext(),
            //array de strings
            R.array.categoria,
            //layout para mostrar el elemento seleccionado
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Layout para mostrar la apariencia de la lista
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // asignamos el adaptador al spinner
            binding.spCategoria.adapter = adapter
            binding.spCategoria.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    //recuperamos el valor
                    val valor=binding.spCategoria.getItemAtPosition(posicion)
                    //creamos el mensaje desde el recurso string parametrizado
                    val mensaje=getString(R.string.mensaje_categoria,valor)
                    //mostramos el mensaje donde "binding.root" es el ContrainLayout principal
                    Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).setAction("Action", null).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
    }

    private fun iniciaSpPrioridad() {
        ArrayAdapter.createFromResource(
            //contexto suele ser la Activity
            requireContext(),
            //array de strings
            R.array.prioridad,
            //layout para mostrar el elemento seleccionado
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Layout para mostrar la apariencia de la lista
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // asignamos el adaptador al spinner
            binding.spPrioridad.adapter = adapter
            binding.spPrioridad.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    //el array son 3 elementos y "alta" ocupa la tercera posición
                    if(posicion==2){
                        binding.clyTarea.setBackgroundColor(requireContext().getColor(R.color.prioridad_alta))
                    }else {//si no es prioridad alta quitamos el color
                        binding.clyTarea.setBackgroundColor(Color.TRANSPARENT)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.clyTarea.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    private fun iniciaSwPagado() {
        binding.swPagado.setOnCheckedChangeListener { _, isChecked ->
            //cambiamos el icono si está marcado o no el switch
            val imagen=if (isChecked) R.drawable.ic_pagado
            else R.drawable.ic_no_pagado
            //asignamos la imagen desde recursos
            binding.ivPagado.setImageResource(imagen)
        }
        //iniciamos a valor false
        binding.swPagado.isChecked=false
        binding.ivPagado.setImageResource(R.drawable.ic_no_pagado)
    }

    private fun iniciaRgEstado() {
        //listener de radioGroup
        binding.rgEstado.setOnCheckedChangeListener { _, checkedId ->
            val imagen= when (checkedId){//el id del RadioButton seleccionado
                //id del cada RadioButon
                R.id.rbAbierta-> R.drawable.ic_abierto
                R.id.rbEnCurso->R.drawable.ic_encurso
                else-> R.drawable.ic_cerrado
            }
            binding.ivEstado.setImageResource(imagen)
        }
        //iniciamos a abierto
        binding.rgEstado.check(R.id.rbAbierta)
    }

    private fun iniciaSbHoras() {
        //asignamos el evento
        binding.sbHorasTrabajadas.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progreso: Int, p2: Boolean) {
                //Mostramos el progreso en el textview
                binding.tvHorasTrabajadas.text=getString(R.string.horas_trabajadas,progreso)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        //inicio del progreso
        binding.sbHorasTrabajadas.progress=0
        binding.tvHorasTrabajadas.text=getString(R.string.horas_trabajadas,0)
    }

    private fun comprobarDatosYGuardar(){
        if (binding.etTecnico.text.toString().isNullOrEmpty() || binding.etDescripcion.text.toString().isNullOrEmpty())
            Snackbar.make(binding.root, "Es necesario rellenar todos los campos",
                Snackbar.LENGTH_LONG).setAction("Action", null).show()
        else{
            guardaTarea()
            val action = TareaFragmentDirections.actionTareaFragmentToListaFragment()
            findNavController().navigate(action)
        }
    }

    private fun guardaTarea() {
        //recuperamos los datos
        val categoria=binding.spCategoria.selectedItemPosition
        val prioridad=binding.spPrioridad.selectedItemPosition
        val pagado=binding.swPagado.isChecked
        val estado=when (binding.rgEstado.checkedRadioButtonId) {
            R.id.rbAbierta -> 0
            R.id.rbEnCurso -> 1
            else -> 2
        }
        val horas=binding.sbHorasTrabajadas.progress
        val valoracion=binding.rbValoracion.rating
        val tecnico=binding.etTecnico.text.toString()
        val descripcion=binding.etDescripcion.text.toString()



        //creamos la tarea: si es nueva, generamos un id, en otro caso le asignamos su id
        lateinit var tareaNueva: Tarea
        if (esNuevo)//nueva tarea
            tareaNueva = Tarea(Tarea.contadorTareas+1, categoria, prioridad, pagado, estado, horas, valoracion, tecnico, descripcion)
        else {
            tareaNueva = Tarea(args.tarea?.id,categoria, prioridad, pagado, estado, horas, valoracion, tecnico, descripcion)
            modificarTarea(args.tarea!!, tareaNueva)
            borrarTarea(args.tarea!!)
        }

        ModelTempTareas.tareas.add(tareaNueva)
        ModelTempTareas.tareas = ModelTempTareas.tareas

    }
}