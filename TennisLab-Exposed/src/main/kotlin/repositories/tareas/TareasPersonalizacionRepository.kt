package repositories.tareas

import entities.PedidosDAO
import entities.tareas.TareasPersonalizacionDAO
import exceptions.tareas.TareaPersonalizacionException
import mappers.tareas.fromTareasPersonalizacionDAOToTareasPersonalizacion
import models.tareas.TareaPersonalizacion
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TareasPersonalizacionRepository(
    private val tareasDAO: IntEntityClass<TareasPersonalizacionDAO>,
    private val pedidosDAO: IntEntityClass<PedidosDAO>
) : ITareasPersonalizacionRepository {

    private val logger = KotlinLogging.logger { }

    override fun findAll(): List<TareaPersonalizacion> = transaction {
        logger.debug { "findAll() - buscando todos" }
        tareasDAO.all().map { it.fromTareasPersonalizacionDAOToTareasPersonalizacion() }
    }

    override fun findById(id: Int): TareaPersonalizacion {
        logger.debug { "findById($id) - buscando $id" }
        return tareasDAO.findById(id)
            ?.fromTareasPersonalizacionDAOToTareasPersonalizacion()
            ?: throw TareaPersonalizacionException("Tarea no encontrada con id $id")
    }

    override fun save(entity: TareaPersonalizacion): TareaPersonalizacion = transaction {
        val existe = tareasDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun update(entity: TareaPersonalizacion, existe: TareasPersonalizacionDAO): TareaPersonalizacion {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            precio = entity.precio
            pedido = pedidosDAO.findById(entity.pedido.id)!!
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromTareasPersonalizacionDAOToTareasPersonalizacion()
    }

    private fun insert(entity: TareaPersonalizacion): TareaPersonalizacion {
        logger.debug { "save($entity) - creando" }
        return tareasDAO.new(entity.id) {
            uuid = entity.uuid
            precio = entity.precio
            pedido = pedidosDAO.findById(entity.pedido.id)!!
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromTareasPersonalizacionDAOToTareasPersonalizacion()
    }

    override fun delete(entity: TareaPersonalizacion): Boolean = transaction {
        val existe = tareasDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}