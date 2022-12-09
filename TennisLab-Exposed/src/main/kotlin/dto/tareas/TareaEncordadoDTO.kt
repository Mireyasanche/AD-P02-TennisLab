/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package dto.tareas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.Pedido

@Serializable
@SerialName("Tarea Encordado")
data class TareaEncordadoDTO(
    val id: Int,
    val uuid: String,
    val precio: Float,
    val pedido: String,
    val tensionHorizontal: Float,
    val cordajeHorizontal: String,
    val tensionVertical: Float,
    val cordajeVertical: String,
    val nudos: String
)
