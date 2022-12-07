package controllers

import entities.PedidosDAO
import entities.ProductosDAO
import models.Producto
import repositories.producto.ProductosRepository

class ProductosController(
    private val productosRepository: ProductosRepository = ProductosRepository(
        ProductosDAO,
        PedidosDAO

)
) {
    fun getProductos(): List<Producto> {
        return productosRepository.findAll()
    }

    fun getProductoById(id: Int): Producto? {
        return productosRepository.findById(id)
    }

    fun saveProducto(entity: Producto): Producto {
        return productosRepository.save(entity)
    }

    fun deleteProducto(entity: Producto): Boolean {
        return productosRepository.delete(entity)
    }
}