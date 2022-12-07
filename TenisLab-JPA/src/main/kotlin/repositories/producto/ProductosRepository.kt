package repositories.producto

import db.HibernateManager
import db.HibernateManager.manager
import models.Producto
import mu.KotlinLogging
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class ProductosRepository : IProductosRepository {
    override fun findAll(): List<Producto> {
        logger.debug { "findAll()" }
        var productos = mutableListOf<Producto>()
        HibernateManager.query {
            val query: TypedQuery<Producto> = manager.createNamedQuery("Producto.findAll", Producto::class.java)
            productos = query.resultList
        }
        return productos
    }

    override fun findById(id: Int): Producto? {
        logger.debug { "findById($id)" }
        var producto: Producto? = null
        HibernateManager.query {
            producto = manager.find(Producto::class.java, id)
        }
        return producto
    }

    override fun save(entity: Producto): Producto {
        logger.debug { "save($entity)" }
        HibernateManager.transaction {
            manager.merge(entity)
        }
        return entity
    }

    override fun delete(entity: Producto): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            val producto = manager.find(Producto::class.java, entity.id)
            producto?.let {
                manager.remove(it)
                result = true
            }
        }
        return result
    }
}