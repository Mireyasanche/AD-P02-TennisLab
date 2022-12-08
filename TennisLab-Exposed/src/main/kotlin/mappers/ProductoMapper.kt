/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package mappers

import entities.ProductosDAO
import models.Producto

/**
 * Esta función de extensión de ProductosDAO se ocupa de convertir el objeto de acceso de datos (DAO)
 * a su tipo original, como estructura en su modelo.
 *
 * @return Producto, el objeto convertido a modelo.
 */
fun ProductosDAO.fromProductosDAOToProducto(): Producto {
    return Producto(
        id = id.value,
        uuid = uuid,
        tipoProducto = tipoProducto,
        marca = marca,
        modelo = modelo,
        precio = precio,
        stock = stock,
        pedido = pedido.fromPedidosDAOToPedidos()
    )
}