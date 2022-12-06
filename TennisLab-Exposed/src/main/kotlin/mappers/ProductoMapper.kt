package mappers

import entities.ProductosDAO
import models.Producto
import models.TipoProducto

fun ProductosDAO.fromProductosDAOToProducto(): Producto {
    return Producto(
        id = id.value,
        uuid = uuid,
        tipoProducto = tipoProducto,
        marca = marca,
        modelo = modelo,
        precio = precio,
        stock = stock,
        pedido = pedido.uuid
    )
}