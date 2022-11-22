package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Producto")
data class ProductoDTO(
    val id: Int,
    val uuid: String,
    val tipoProducto: String,
    val marca: String,
    val modelo: String,
    val precio: Float,
    val stock: Int
)
