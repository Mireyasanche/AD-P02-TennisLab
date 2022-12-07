package repositories.producto

import models.Producto

class ProductosRepository: IProductosRepository {
    override fun findAll(): List<Producto> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): Producto? {
        TODO("Not yet implemented")
    }

    override fun save(entity: Producto): Producto {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Producto): Boolean {
        TODO("Not yet implemented")
    }
}