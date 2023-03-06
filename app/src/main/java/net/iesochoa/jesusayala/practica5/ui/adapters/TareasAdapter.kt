package net.iesochoa.jesusayala.practica5.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iesochoa.jesusayala.practica5.databinding.ItemTareaBinding
import net.iesochoa.jesusayala.practica5.model.Tarea

class TareasAdapter (private var listaTareas: List<Tarea>)
    : RecyclerView.Adapter<TareasAdapter.TareaViewHolder>(){

    fun setLista(lista:List<Tarea>){
        listaTareas=lista
        //notifica al adaptador que hay cambios y tiene que redibujar el ReciclerView
        notifyDataSetChanged()
    }

    inner class TareaViewHolder(val binding: ItemTareaBinding)
        :RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType:
    Int): TareaViewHolder {
        TODO("Not yet implemented")
    }
    override fun onBindViewHolder(holder: TareaViewHolder, position:
    Int) {
        TODO("Not yet implemented")
    }
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}