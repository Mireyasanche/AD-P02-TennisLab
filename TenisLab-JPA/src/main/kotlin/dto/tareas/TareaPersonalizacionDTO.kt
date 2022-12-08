/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package dto.tareas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.Pedido

@Serializable
@SerialName("Tarea Personalizacion")
data class TareaPersonalizacionDTO(
    val id: Int,
    val uuid: String,
    val precio: Float,
    val pedido: String,
    val peso: Float,
    val balance: Float,
    val rigidez: Float
)
