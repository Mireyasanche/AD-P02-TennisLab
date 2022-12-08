/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.Pedido

@Serializable
@SerialName("Pedido")
data class PedidoDTO(
    val id: Int,
    val uuid: String,
    val estado: String,
    val encordador: String,
    val fechaTope: String,
    val fechaEntrada: String,
    val fechaProgramada: String,
    val fechaEntrega: String,
    val precio: Float
)

/**
 * Esta función de extensión de Pedido se ocupa de convertir el objeto de tipos transferencia de datos (DTO) para poder
 * pasar la información del mismo a ficheros de una forma más sencilla, evitando los tipos complejos.
 *
 * @return PedidoDTO, el objeto convertido en DTO.
 */
fun Pedido.toDTO(): PedidoDTO {
    return PedidoDTO(
        id = id,
        uuid = uuid.toString(),
        estado = estado.toString(),
        encordador = encordador.toString(),
        fechaTope = fechaTope.toString(),
        fechaEntrega = fechaEntrega.toString(),
        fechaProgramada = fechaProgramada.toString(),
        fechaEntrada = fechaEntrada.toString(),
        precio = precio
    )
}
