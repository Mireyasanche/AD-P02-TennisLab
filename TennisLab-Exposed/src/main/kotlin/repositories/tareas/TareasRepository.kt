package repositories.tareas

import entities.tareas.TareasDAO
import exceptions.tareas.TareaException
import models.tareas.Tarea
import mu.KotlinLogging
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TareasRepository(
    private val tareasDAO: UUIDEntityClass<TareasDAO>
): ITareasRepository {

    private val logger = KotlinLogging.logger { }

    override fun findAll(): List<Tarea> = transaction {
        logger.debug { "findAll() - buscando todos" }
        tareasDAO.all().map { it.() }
    }

    override fun findById(id: UUID): Tarea? {
        logger.debug { "findById($id) - buscando $id" }
        tareasDAO.findById(id)
            ?.fromTareasDAOToTareas()?: throw TareaException("Tarea no encontrada con id $id")
    }

    override fun save(entity: Tarea): Tarea = transaction {
        val existe = tareasDAO.findById(entity.uuid)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun update(entity: Tarea, existe: TareasDAO): Tarea {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            precio = entity.precio
        }.fromTareasDAOToTareas()
    }

    private fun insert(entity: Tarea): Tarea {
        logger.debug { "save($entity) - creando" }
        return tareasDAO.new(entity.uuid) {
            precio = entity.precio
        }.fromTareasDAOToTareas()
    }

    override fun delete(entity: Tarea): Boolean = transaction {
        val existe = tareasDAO.findById(entity.uuid) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}