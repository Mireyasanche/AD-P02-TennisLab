package repositories.tareas

import entities.PedidosDAO
import entities.tareas.TareasEncordadoDAO
import exceptions.tareas.TareaEncordadoException
import mappers.tareas.fromTareasEncordadoDAOToTareasEncordado
import models.tareas.TareaEncordado
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TareasEncordadoEncordadoRepository(
    private val tareasDAO: IntEntityClass<TareasEncordadoDAO>,
    private val pedidosDAO: IntEntityClass<PedidosDAO>
) : ITareasEncordadoRepository {

    private val logger = KotlinLogging.logger { }

    override fun findAll(): List<TareaEncordado> = transaction {
        logger.debug { "findAll() - buscando todos" }
        tareasDAO.all().map { it.fromTareasEncordadoDAOToTareasEncordado() }
    }

    override fun findById(id: Int): TareaEncordado {
        logger.debug { "findById($id) - buscando $id" }
        return tareasDAO.findById(id)
            ?.fromTareasEncordadoDAOToTareasEncordado()
            ?: throw TareaEncordadoException("Tarea no encontrada con id $id")
    }

    override fun save(entity: TareaEncordado): TareaEncordado = transaction {
        val existe = tareasDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun update(entity: TareaEncordado, existe: TareasEncordadoDAO): TareaEncordado {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            pedido = pedidosDAO.findById(entity.pedido.id)!!
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeVertical = entity.cordajeVertical
            cordajeHorizontal = entity.cordajeHorizontal
            nudos = entity.nudos.toString()
            precio = entity.precio
        }.fromTareasEncordadoDAOToTareasEncordado()
    }

    private fun insert(entity: TareaEncordado): TareaEncordado {
        logger.debug { "save($entity) - creando" }
        return tareasDAO.new(entity.id) {
            uuid = entity.uuid
            pedido = pedidosDAO.findById(entity.pedido.id)!!
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeVertical = entity.cordajeVertical
            cordajeHorizontal = entity.cordajeHorizontal
            nudos = entity.nudos.toString()
            precio = entity.precio
        }.fromTareasEncordadoDAOToTareasEncordado()
    }

    override fun delete(entity: TareaEncordado): Boolean = transaction {
        val existe = tareasDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}