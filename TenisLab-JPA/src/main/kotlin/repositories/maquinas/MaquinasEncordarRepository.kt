package repositories.maquinas

import models.maquinas.MaquinaEncordar
import db.HibernateManager
import db.HibernateManager.manager
import mu.KotlinLogging
import repositories.turno.TurnosRepository
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class MaquinasEncordarRepository: IMaquinasEncordarRepository {
    private val turnosRepository = TurnosRepository()
    override fun findAll(): List<MaquinaEncordar> {
        logger.debug { "findAll()" }
        var maquinas = mutableListOf<MaquinaEncordar>()
        HibernateManager.query {
            val query: TypedQuery<MaquinaEncordar> = manager.createNamedQuery("MaquinaEncordar.findAll", MaquinaEncordar::class.java)
            maquinas = query.resultList
        }
        return maquinas
    }

    override fun findById(id: Int): MaquinaEncordar? {
        logger.debug { "findById($id)" }
        var maquina: MaquinaEncordar? = null
        HibernateManager.query {
            maquina = manager.find(MaquinaEncordar::class.java, id)
        }
        return maquina
    }

    override fun save(entity: MaquinaEncordar): MaquinaEncordar {
        val encordadorParaAsignar = entity.turno.encordador
        val encordadoresAsignados = turnosRepository.findAll().filter { it.id == entity.turno.id}.map { it.encordador}
        require(!encordadoresAsignados.contains(encordadorParaAsignar))
        {"Esta máquina no ha podido añadirse porque el encordador que quiere asignarle ya esta utilizando otra máquina en ese turno."}

        logger.debug { "save($entity)" }
        HibernateManager.transaction {
            manager.merge(entity)
        }
        return entity
    }

    override fun delete(entity: MaquinaEncordar): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            val maquina = manager.find(MaquinaEncordar::class.java, entity.id)
            maquina?.let {
                manager.remove(it)
                result = true
            }
        }
        return result
    }
}