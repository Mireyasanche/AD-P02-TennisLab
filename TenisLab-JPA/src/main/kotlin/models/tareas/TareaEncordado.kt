package models.tareas

import models.Pedido
import java.util.*

class TareaEncordado(
    override val id: Int,
    override val uuid: UUID,
    override val precio: Float,
    override val pedido: Pedido,
    val tensionHorizontal: Float,
    val cordajeHorizontal: String,
    val tensionVertical: Float,
    val cordajeVertical: String,
    val nudos: NumeroNudos
) : Tarea

enum class NumeroNudos(numeroNudos: String) {
    DOS("DOS"), CUATRO("CUATRO");

    companion object {
        fun from(numeroNudos: String): NumeroNudos {
            return when (numeroNudos.uppercase()) {
                "DOS" -> DOS
                "CUATRO" -> CUATRO
                else -> throw IllegalArgumentException("Número de nudos no válido.")
            }
        }
    }
}