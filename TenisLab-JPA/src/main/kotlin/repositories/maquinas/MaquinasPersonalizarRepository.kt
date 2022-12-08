package repositories.maquinas

import db.HibernateManager
import db.HibernateManager.manager
import models.maquinas.MaquinaPersonalizar
import mu.KotlinLogging
import repositories.turno.TurnosRepository
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}
class MaquinasPersonalizarRepository: IMaquinasPersonalizarRepository {
    private val turnosRepository = TurnosRepository()
    override fun findAll(): List<MaquinaPersonalizar> {
        logger.debug { "findAll()" }
        var maquinas = mutableListOf<MaquinaPersonalizar>()
        HibernateManager.query {
            val query: TypedQuery<MaquinaPersonalizar> = manager.createNamedQuery("MaquinaPersonalizar.findAll", MaquinaPersonalizar::class.java)
            maquinas = query.resultList
        }
        return maquinas
    }

    override fun findById(id: Int): MaquinaPersonalizar? {
        logger.debug { "findById($id)" }
        var maquina: MaquinaPersonalizar? = null
        HibernateManager.query {
            maquina = manager.find(MaquinaPersonalizar::class.java, id)
        }
        return maquina
    }

    override fun save(entity: MaquinaPersonalizar): MaquinaPersonalizar {
        val encordadorParaAsignar = entity.turno.encordador
        val encordadoresAsignados = turnosRepository.findAll().filter { it.id == entity.turno.id}.map { it.encordador}
        require(!encordadoresAsignados.contains(encordadorParaAsignar))
        {"Esta máquina no ha podido añadirse porque el encordador que quiere asignarle ya esta utilizando otra máquina en ese turno."}

        logger.debug { "save($entity)" }
        HibernateManager.transaction {
            manager.merge(entity)
        }
        return entity    }

    override fun delete(entity: MaquinaPersonalizar): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            // Ojo que borrar implica que estemos en la misma sesión y nos puede dar problemas, por eso lo recuperamos otra vez
            val maquina = manager.find(MaquinaPersonalizar::class.java, entity.id)
            maquina?.let {
                manager.remove(it)
                result = true
            }
        }
        return result
    }
}