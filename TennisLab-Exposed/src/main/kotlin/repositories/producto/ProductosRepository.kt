package repositories.producto

import entities.PedidosDAO
import entities.ProductosDAO
import exceptions.ProductoException
import mappers.fromProductosDAOToProducto
import models.Producto
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class ProductosRepository(
    private val productosDAO: IntEntityClass<ProductosDAO>,
    private val pedidosDAO: IntEntityClass<PedidosDAO>
): IProductosRepository {

    private val logger = KotlinLogging.logger { }

    override fun findAll(): List<Producto> = transaction {
        logger.debug { "findAll() - buscando todos" }
        productosDAO.all().map { it.fromProductosDAOToProducto() }
    }

    override fun findById(id: Int): Producto = transaction {
        logger.debug { "findById($id) - buscando $id" }
        productosDAO.findById(id)
            ?.fromProductosDAOToProducto()?: throw ProductoException("Producto no encontrado con id: $id")

    }

    override fun save(entity: Producto): Producto = transaction {
        val existe = productosDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Producto): Producto {
        logger.debug { "save($entity) - creando" }
        return productosDAO.new(entity.id) {
            uuid = entity.uuid
            tipoProducto = entity.tipoProducto
            marca = entity.marca
            modelo = entity.modelo
            precio = entity.precio
            stock = entity.stock
            pedido = pedidosDAO.findById(entity.pedido.id)!!
        }.fromProductosDAOToProducto()
    }

    private fun update(entity: Producto, existe: ProductosDAO): Producto {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            tipoProducto = entity.tipoProducto
            marca = entity.marca
            modelo = entity.modelo
            precio = entity.precio
            stock = entity.stock
            pedido = pedidosDAO.findById(entity.pedido.id)!!
        }.fromProductosDAOToProducto()
    }

    override fun delete(entity: Producto): Boolean = transaction {
        val existe = productosDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}