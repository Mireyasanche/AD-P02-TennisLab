/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
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
    /**
     * Método encargado de ejecutar una NamedQuery anteriormente nombrada en su respectivo modelo y la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<MaquinaPersonalizar>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    override fun findAll(): List<MaquinaPersonalizar> {
        logger.debug { "findAll()" }
        var maquinas = mutableListOf<MaquinaPersonalizar>()
        HibernateManager.query {
            val query: TypedQuery<MaquinaPersonalizar> = manager.createNamedQuery("MaquinaPersonalizar.findAll", MaquinaPersonalizar::class.java)
            maquinas = query.resultList
        }
        return maquinas
    }

    /**
     * Método encargado de ejecutar una consulta la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return MaquinaPersonalizar?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    override fun findById(id: Int): MaquinaPersonalizar? {
        logger.debug { "findById($id)" }
        var maquina: MaquinaPersonalizar? = null
        HibernateManager.query {
            maquina = manager.find(MaquinaPersonalizar::class.java, id)
        }
        return maquina
    }

    /**
     * Método encargado de ejecutar una inserción de datos en la base de datos.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     * Ademas será necesario que el objeto en cuestión cuente con un encordador que no esté utilizando ya otra máquina en ese mismo turno
     * para poder añadirse sin problemas.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return MaquinaPersonalizar, el objeto que ha sido insertado o actualizado.
     */
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

    /**
     * Método encargado de ejecutar un borrado de datos en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
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