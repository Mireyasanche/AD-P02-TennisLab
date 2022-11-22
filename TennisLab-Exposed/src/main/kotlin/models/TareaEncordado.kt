package models

import java.util.*

class TareaEncordado(
    id: Int,
    uuid: UUID,
    precio: Float,
    val tensionHorizontal: Float,
    val cordajeHorizontal: String,
    val tensionVertical: Float,
    val cordajeVertical: String,
    val nudos: NumeroNudos
    ): Tarea(id, uuid, precio)

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