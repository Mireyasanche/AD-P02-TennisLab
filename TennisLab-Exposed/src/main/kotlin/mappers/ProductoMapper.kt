package mappers

import entities.ProductosDAO
import models.Producto

fun ProductosDAO.fromProductosDAOToProducto(): Producto {
    return Producto(
        id = id.value,
        uuid = uuid,
        tipoProducto = tipoProducto,
        marca = marca,
        modelo = modelo,
        precio = precio,
        pedido = pedido.uuid,
        stock = stock
    )
}