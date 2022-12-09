/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package repositories.tareas

import db.HibernateManager
import models.tareas.TareaPersonalizacion
import mu.KotlinLogging
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class TareasPersonalizacionRepository: ITareasPersonalizacionRepository {
    /**
     * Método encargado de ejecutar una NamedQuery anteriormente nombrada en su respectivo modelo y la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<TareaPersonalizacion>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    override fun findAll(): List<TareaPersonalizacion> {
        logger.debug { "findAll()" }
        var tareas = mutableListOf<TareaPersonalizacion>()
        HibernateManager.query {
            val query: TypedQuery<TareaPersonalizacion> = HibernateManager.manager.createNamedQuery("TareaPersonalizacion.findAll", TareaPersonalizacion::class.java)
            tareas = query.resultList
        }
        return tareas    }

    /**
     * Método encargado de ejecutar una consulta la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return TareaPersonalizacion?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    override fun findById(id: Int): TareaPersonalizacion? {
        logger.debug { "findById($id)" }
        var tarea: TareaPersonalizacion? = null
        HibernateManager.query {
            tarea = HibernateManager.manager.find(TareaPersonalizacion::class.java, id)
        }
        return tarea      }

    /**
     * Método encargado de ejecutar una inserción de datos en la base de datos.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return MaquinaEncordar, el objeto que ha sido insertado o actualizado.
     */
    override fun save(entity: TareaPersonalizacion): TareaPersonalizacion {
        logger.debug { "save($entity)" }
        HibernateManager.transaction {
            HibernateManager.manager.merge(entity)
        }
        return entity    }

    /**
     * Método encargado de ejecutar un borrado de datos en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    override fun delete(entity: TareaPersonalizacion): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            val usuario = HibernateManager.manager.find(TareaPersonalizacion::class.java, entity.id)
            usuario?.let {
                HibernateManager.manager.remove(it)
                result = true
            }
        }
        return result    }
}