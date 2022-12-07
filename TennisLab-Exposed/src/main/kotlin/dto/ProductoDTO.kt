package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.Pedido
import models.Producto

@Serializable
@SerialName("Producto")
data class ProductoDTO(
    val id: Int,
    val uuid: String,
    val tipoProducto: String,
    val marca: String,
    val modelo: String,
    val precio: Float,
    val stock: Int,
    val pedido: String
)

fun Producto.toDTO(): ProductoDTO {
    return ProductoDTO(
        id = id,
        uuid = uuid.toString(),
        tipoProducto = tipoProducto.toString(),
        marca = marca.toString(),
        modelo = modelo.toString(),
        precio = precio,
        stock = stock,
        pedido = pedido.toString()
    )
}
