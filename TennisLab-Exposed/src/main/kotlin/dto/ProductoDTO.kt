/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
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

/**
 * Esta función de extensión de Producto se ocupa de convertir el objeto de tipos transferencia de datos (DTO) para poder
 * pasar la información del mismo a ficheros de una forma más sencilla, evitando los tipos complejos.
 *
 * @return ProductoDTO, el objeto convertido en DTO.
 */
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
