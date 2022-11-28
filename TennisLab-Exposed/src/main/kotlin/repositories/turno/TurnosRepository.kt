package repositories.turno

import entities.TurnosDAO
import entities.UsuariosDAO
import entities.maquinas.MaquinasDAO
import exceptions.TurnoException
import exceptions.UsuarioException
import exceptions.maquinas.MaquinaException
import mappers.fromTurnoDAOToTurno
import models.Turno
import mu.KotlinLogging
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TurnosRepository(
    private val turnosDAO: UUIDEntityClass<TurnosDAO>,
    private val usuariosDAO: UUIDEntityClass<UsuariosDAO>,
    private val maquinasDAO: UUIDEntityClass<MaquinasDAO>

): ITurnosRepository{
    private val logger = KotlinLogging.logger {}

    override fun findAll(): List<Turno> = transaction {
        logger.debug { "findAll() - buscando todos " }
        turnosDAO.all().map { it.fromTurnoDAOToTurno() }
    }

    override fun findById(id: UUID): Turno? = transaction {
        logger.debug { "findById($id) - buscando $id" }
        turnosDAO.findById(id)
            ?.fromTurnoDAOToTurno()?: throw TurnoException("Turno no encontrado con id: $id")
    }

    override fun save(entity: Turno): Turno = transaction {
        val existe = turnosDAO.findById(entity.uuid)

        existe?.let {
            // Si existe actualizamos
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    override fun delete(entity: Turno): Boolean = transaction {
        val existe = turnosDAO.findById(entity.uuid) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

    private fun insert(entity: Turno): Turno {
        logger.debug { "save($entity) - creando" }
        return turnosDAO.new(entity.uuid) {
            comienzo = entity.comienzo
            final = entity.final
            maquina = maquinasDAO.findById(entity.maquina!!.uuid)
                ?: throw MaquinaException("La máquina con id: $id no existe.")
            encordador = usuariosDAO.findById(entity.encordador!!.uuid)
                ?: throw UsuarioException("El encordador con id: $id no existe.")
        }.fromTurnoDAOToTurno()
    }

    private fun update(entity: Turno, existe: TurnosDAO): Turno {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            comienzo = entity.comienzo
            final = entity.final
            maquina = maquinasDAO.findById(entity.maquina!!.uuid)
                ?: throw MaquinaException("La máquina con id: $id no existe.")
            encordador = usuariosDAO.findById(entity.encordador!!.uuid)
                ?: throw UsuarioException("El encordador con id: $id no existe.")
        }.fromTurnoDAOToTurno()
    }
}