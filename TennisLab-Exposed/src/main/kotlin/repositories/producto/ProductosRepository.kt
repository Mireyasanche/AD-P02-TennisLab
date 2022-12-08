/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.producto

import entities.PedidosDAO
import entities.ProductosDAO
import exceptions.ProductoException
import exceptions.maquinas.MaquinaEncordarException
import mappers.fromProductosDAOToProducto
import models.Producto
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class ProductosRepository(
    private val productosDAO: IntEntityClass<ProductosDAO>,
    private val pedidosDAO: IntEntityClass<PedidosDAO>
) : IProductosRepository {

    private val logger = KotlinLogging.logger { }

    /**
     * Método encargado de ejecutar transacción, con una consulta de los DAO en su interior, la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<Producto>, la lista de objetos encontrada en su respectiva base de datos convertido de DAO a modelo.
     */
    override fun findAll(): List<Producto> = transaction {
        logger.debug { "findAll() - buscando todos" }
        productosDAO.all().map { it.fromProductosDAOToProducto() }
    }

    /**
     * Método encargado de ejecutar transacción, con una consulta del DAO en su interior, la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar.
     *
     * @throws MaquinaEncordarException si la maquina no se encuentra.
     *
     * @return Producto, el objeto que tiene el identificador introducido convertido de DAO a modelo.
     */
    override fun findById(id: Int): Producto = transaction {
        logger.debug { "findById($id) - buscando $id" }
        productosDAO.findById(id)
            ?.fromProductosDAOToProducto() ?: throw ProductoException("Producto no encontrado con id: $id")

    }

    /**
     * Método encargado de ejecutar transacción, con una inserción del DAO a la base de datos en su interior.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Producto, el objeto que ha sido insertado o actualizado convertido de DAO a modelo.
     */
    override fun save(entity: Producto): Producto = transaction {
        val existe = productosDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }


    /**
     * Método encargado de realizar la inserción del DAO en caso de que la entidad a introducir no exista en la base de datos.
     *
     * @param entity objeto a insetar.
     *
     * @return Producto, el objeto que ha sido insertado convertido de DAO a modelo.
     */
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

    /**
     * Método encargado de realizar la inserción del DAO en caso de que la entidad a introducir no exista en la base de datos.
     *
     * @param entity objeto a insetar.
     * @param existe objeto DAO si existe, nulo si no.
     *
     * @return Producto, el objeto que ha sido insertado convertido de DAO a modelo.
     */
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

    /**
     * Método encargado de ejecutar transacción, con un borrado del DAO de la base de datos en su interior.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar convertido de DAO a modelo.
     */
    override fun delete(entity: Producto): Boolean = transaction {
        val existe = productosDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}