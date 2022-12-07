package controllers

import models.Producto
import repositories.producto.ProductosRepository

class ProductosController(
    private val productosRepository: ProductosRepository = ProductosRepository()
) {
    fun getProductos(): List<Producto> {
        return productosRepository.findAll()
    }

    fun getProductos(id: Int): Producto? {
        return productosRepository.findById(id)
    }

    fun saveProductos(entity: Producto): Producto {
        return productosRepository.save(entity)
    }

    fun deleteProductos(entity: Producto): Boolean {
        return productosRepository.delete(entity)
    }
}