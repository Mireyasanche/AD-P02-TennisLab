package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
