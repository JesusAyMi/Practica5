package net.iesochoa.jesusayala.practica5.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iesochoa.jesusayala.practica5.R
import net.iesochoa.jesusayala.practica5.databinding.ItemTareaBinding
import net.iesochoa.jesusayala.practica5.model.Tarea
import net.iesochoa.jesusayala.practica5.model.temp.ModelTempTareas.tareas

class TareasAdapter ()
    : RecyclerView.Adapter<TareasAdapter.TareaViewHolder>(){
    var listaTareas: List<Tarea>?=null
    var onTareaClickListener:OnTareaClickListener?=null
    fun setLista(lista:List<Tarea>){
        listaTareas=lista
        //notifica al adaptador que hay cambios y tiene que redibujar el ReciclerView
        notifyDataSetChanged()
    }

    inner class TareaViewHolder(val binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root){
        init {
            //inicio del click de icono borrar
            binding.ivBorrar.setOnClickListener(){
                //recuperamos la tarea de la lista
                val tarea= listaTareas?.get(this.adapterPosition)
                //llamamos al evento borrar que estará definido en el fragment
                onTareaClickListener?.onTareaBorrarClick(tarea)
            }
            //inicio del click sobre el Layout(constraintlayout)
            binding.root.setOnClickListener(){
                val tarea= listaTareas?.get(this.adapterPosition)
                onTareaClickListener?.onTareaClick(tarea)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TareaViewHolder {
        val binding = ItemTareaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TareaViewHolder(binding)
    }
    override fun onBindViewHolder(tareaViewHolder: TareaViewHolder, pos:
    Int) {
        //Nos pasan la posición del item a mostrar en el viewHolder
        with(tareaViewHolder) {
            //cogemos la tarea a mostrar y rellenamos los campos del ViewHolder
            with(listaTareas!!.get(pos)) {
                binding.tvId.text = id.toString()
                binding.tvDescripcion.text = descripcion
                binding.tvTecnico.text = tecnico
                binding.rbVal.rating = valoracion
                //mostramos el icono en función del estado
                binding.ivEst.setImageResource(
                    when (estado) {
                        0 -> R.drawable.ic_abierto
                        1 -> R.drawable.ic_encurso
                        else -> R.drawable.ic_cerrado
                    }
                )
                //cambiamos el color de fondo si la prioridad es alta
                binding.cvItem.setBackgroundResource(
                    if (prioridad == 2)//prioridad alta
                        R.color.prioridad_alta
                    else
                        Color.TRANSPARENT
                )
            }
        }
    }
    override fun getItemCount(): Int {
        return tareas.size
    }

    interface OnTareaClickListener{
        //editar tarea que contiene el ViewHolder
        fun onTareaClick(tarea:Tarea?)
        //borrar tarea que contiene el ViewHolder
        fun onTareaBorrarClick(tarea:Tarea?)
    }
}
