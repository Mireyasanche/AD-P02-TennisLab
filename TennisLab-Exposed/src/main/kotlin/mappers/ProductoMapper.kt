package mappers

import entities.ProductosDAO
import models.Producto
import models.TipoProducto

fun ProductosDAO.fromProductosDAOToProducto(): Producto {
    return Producto(
        id = 0,
        uuid = id.value,
        tipoProducto = TipoProducto.from(tipoProducto),
        marca = marca,
        modelo = modelo,
        precio = precio,
        stock = stock
    )
}