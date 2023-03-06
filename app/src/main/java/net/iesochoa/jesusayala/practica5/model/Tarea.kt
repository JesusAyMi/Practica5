package net.iesochoa.jesusayala.practica5.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tareas")
@Parcelize
data class Tarea(
    @PrimaryKey(autoGenerate = true) var id: Long?, val categoria: Int, val prioridad: Int, val pagado: Boolean,
    val estado: Int, val horas: Int, val valoracion: Float, val tecnico: String,
    val descripcion: String): Parcelable {


    companion object {
        var contadorTareas: Long = 0
    }

    constructor(
        categoria: Int, prioridad: Int, pagado: Boolean, estado: Int, horas: Int,
        valoracion: Float, tecnico: String, descripcion: String) : this(
        null,
        categoria,
        prioridad,
        pagado,
        estado,
        horas,
        valoracion,
        tecnico,
        descripcion
    ) {
        this.id = ++contadorTareas
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tarea) return false
        if (id != other.id) return false
        return true
    }
}

