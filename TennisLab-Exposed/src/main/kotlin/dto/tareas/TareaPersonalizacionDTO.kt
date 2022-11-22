package dto.tareas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Tarea Personalizacion")
data class TareaPersonalizacionDTO(
    val id: Int,
    val uuid: String,
    val precio: Float,
    val peso: Float,
    val balance: Float,
    val rigidez: Float
)
