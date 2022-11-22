package dto.tareas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Tarea Encordado")
data class TareaEncordadoDTO(
    val id: Int,
    val uuid: String,
    val precio: Float,
    val tensionHorizontal: Float,
    val cordajeHorizontal: String,
    val tensionVertical: Float,
    val cordajeVertical: String,
    val nudos: String
)
