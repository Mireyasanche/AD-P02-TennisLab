package repositories.turno

import models.Turno
import mu.KotlinLogging
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class TurnosRepository: ITurnosRepository {
    override fun findAll(): List<Turno> {
        logger.debug { "findAll()" }
        var turnos = mutableListOf<Turno>()
        HibernateManager.query {
            val query: TypedQuery<Turno> = manager.createNamedQuery("Turnos.findAll()", Turno::class.java)
            turnos = query.resultList
        }
        return turnos
    }

    override fun findById(id: Int): Turno? {
        logger.debug { "findById($id)" }
        var turno: Turno? = null
        HibernateManager.query {
            turno = manager.find(Turno::class.java, id)
        }
        return turno
    }

    override fun save(entity: Turno): Turno {
        logger.debug { "save($entity)" }
        HibernateManager.transaction {
            manager.merge(entity)
        }
        return entity
    }

    override fun delete(entity: Turno): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            val tenista = manager.find(Turno::class.java, entity.uuid)
            tenista?.let {
                manager.remove(it)
                result = true
            }
        }
        return result
    }
}