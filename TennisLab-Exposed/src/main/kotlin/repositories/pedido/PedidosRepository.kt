/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.pedido

import entities.PedidosDAO
import entities.UsuariosDAO
import exceptions.PedidoException
import exceptions.UsuarioException
import mappers.fromPedidosDAOToPedidos
import models.Pedido
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.usuario.UsuariosRepository

class PedidosRepository(
    private val pedidosDAO: IntEntityClass<PedidosDAO>,
    private val usuariosDAO: IntEntityClass<UsuariosDAO>,

) : IPedidosRepository {
    private val logger = KotlinLogging.logger {}
    private val usuariosRepository =  UsuariosRepository(usuariosDAO)

    /**
     * Método encargado de ejecutar transacción, con una consulta de los DAO en su interior, la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<MaquinaEncordar>, la lista de objetos encontrada en su respectiva base de datos convertido de DAO a modelo.
     */
    override fun findAll(): List<Pedido> = transaction {
        logger.debug { "findAll() - buscando todos " }
        pedidosDAO.all().map { it.fromPedidosDAOToPedidos() }
    }

    /**
     * Método encargado de ejecutar transacción, con una consulta del DAO en su interior, la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar.
     *
     * *@throws PedidoException si la maquina no se encuentra.
     *
     * @return Pedido, el objeto que tiene el identificador introducido convertido de DAO a modelo.
     */
    override fun findById(id: Int): Pedido = transaction {
        logger.debug { "findById($id) - buscando $id" }
        pedidosDAO.findById(id)
            ?.fromPedidosDAOToPedidos() ?: throw PedidoException("Pedido no encontrado con id: $id")
    }

    /**
     * Método encargado de ejecutar transacción, con una inserción del DAO a la base de datos en su interior.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     * Ademas será necesario que el objeto en cuestión cuente con un encordador que no esté asignado ya a otros dos pedidos
     * para poder añadirse sin problemas.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Pedido, el objeto que ha sido insertado o actualizado convertido de DAO a modelo.
     */
    override fun save(entity: Pedido): Pedido = transaction {
        val existe = pedidosDAO.findById(entity.id)

        require(findAll()
            .filter { it.encordador.id == entity.encordador.id }
            .map { it.encordador }
            .count() < 2 ) {"Este pedido no ha podido guardarse correctamente ya que su encordador asignado ya tiene dos pedidos asignados."}

            existe?.let {
                update(entity, existe)
            } ?: run {
                insert(entity)
            }

    }

    /**
     * Método encargado de ejecutar transacción, con un borrado del DAO de la base de datos en su interior.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar convertido de DAO a modelo.
     */
    override fun delete(entity: Pedido): Boolean = transaction {
        val existe = pedidosDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

    /**
     * Método encargado de realizar la inserción del DAO en caso de que la entidad a introducir no exista en la base de datos.
     *
     * @param entity objeto a insetar.
     *
     * @return Pedido, el objeto que ha sido insertado convertido de DAO a modelo.
     */
    private fun insert(entity: Pedido): Pedido {
        logger.debug { "save($entity) - creando" }
        return pedidosDAO.new(entity.id) {
            uuid = entity.uuid
            estado = entity.estado.toString()
            encordador = usuariosDAO.findById(entity.encordador!!.id)
                ?: throw UsuarioException("El encordador con id: $id no existe.")
            fechaTope = entity.fechaTope
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaEntrega = entity.fechaEntrega
            precio = entity.precio
        }.fromPedidosDAOToPedidos()
    }

    /**
     * Método encargado de realizar la actualización del DAO en caso de que la entidad a introducir exista en la base de datos.
     *
     * @param entity objeto a actualizar.
     * @param existe objeto DAO si existe, nulo si no.
     *
     * @return Pedido, el objeto que ha sido actualizado convertido de DAO a modelo.
     */
    private fun update(entity: Pedido, existe: PedidosDAO): Pedido {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            estado = entity.estado.toString()
            encordador = usuariosDAO.findById(entity.encordador!!.id)
                ?: throw UsuarioException("El encordador con id: $id no existe.")
            fechaTope = entity.fechaTope
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaEntrega = entity.fechaEntrega
            precio = entity.precio

        }.fromPedidosDAOToPedidos()
    }
}