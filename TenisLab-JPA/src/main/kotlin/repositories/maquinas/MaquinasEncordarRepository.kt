package repositories.maquinas

import models.maquinas.MaquinaEncordar
import db.HibernateManager
import db.HibernateManager.manager
import mu.KotlinLogging
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class MaquinasEncordarRepository: IMaquinasEncordarRepository {
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