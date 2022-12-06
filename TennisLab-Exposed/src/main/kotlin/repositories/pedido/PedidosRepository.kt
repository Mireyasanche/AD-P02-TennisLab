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

class PedidosRepository(
    private val pedidosDAO: IntEntityClass<PedidosDAO>,
    private val usuariosDAO: IntEntityClass<UsuariosDAO>,
) : IPedidosRepository {
    private val logger = KotlinLogging.logger {}

    override fun findAll(): List<Pedido> = transaction {
        logger.debug { "findAll() - buscando todos " }
        pedidosDAO.all().map { it.fromPedidosDAOToPedidos() }
    }

    override fun findById(id: Int): Pedido = transaction {
        logger.debug { "findById($id) - buscando $id" }
        pedidosDAO.findById(id)
            ?.fromPedidosDAOToPedidos() ?: throw PedidoException("Pedido no encontrado con id: $id")
    }

    override fun save(entity: Pedido): Pedido = transaction {
        val existe = pedidosDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    override fun delete(entity: Pedido): Boolean = transaction {
        val existe = pedidosDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

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