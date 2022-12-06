package models

import java.util.*

data class Producto(
    val id: Int,
    val uuid: UUID,
    val tipoProducto: TipoProducto,
    val marca: String,
    val modelo: String,
    val precio: Float,
    val stock: Int,
    val pedido: UUID
)

enum class TipoProducto(val tipoProducto: String) {
    RAQUETA("RAQUETA"), CORDAJE("CORDAJE"), COMPLEMENTO("COMPLEMENTO");

    companion object {
        fun from(producto: String): TipoProducto {
            return when (producto.uppercase()) {
                "RAQUETA" -> RAQUETA
                "CORDAJE" -> CORDAJE
                "COMPLEMENTO" -> COMPLEMENTO
                else -> throw IllegalArgumentException("Producto no válido.")
            }
        }
    }
}