/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package repositories.tareas

import db.HibernateManager
import db.HibernateManager.manager
import models.tareas.TareaEncordado
import mu.KotlinLogging
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}
class TareasEncordadoRepository : ITareasEncordadoRepository{
    /**
     * Método encargado de ejecutar una NamedQuery anteriormente nombrada en su respectivo modelo y la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<TareaEncordado>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    override fun findAll(): List<TareaEncordado> {
        logger.debug { "findAll()" }
        var tareas = mutableListOf<TareaEncordado>()
        HibernateManager.query {
            val query: TypedQuery<TareaEncordado> = manager.createNamedQuery("TareaEncordado.findAll", TareaEncordado::class.java)
            tareas = query.resultList
        }
        return tareas
    }
    /**
     * Método encargado de ejecutar una consulta la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return TareaEncordado?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    override fun findById(id: Int): TareaEncordado? {
        logger.debug { "findById($id)" }
        var tarea: TareaEncordado? = null
        HibernateManager.query {
            tarea = manager.find(TareaEncordado::class.java, id)
        }
        return tarea
    }

    /**
     * Método encargado de ejecutar una inserción de datos en la base de datos.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return MaquinaEncordar, el objeto que ha sido insertado o actualizado.
     */
    override fun save(entity: TareaEncordado): TareaEncordado {
        logger.debug { "save($entity)" }
        HibernateManager.transaction {
            manager.merge(entity)
        }
        return entity
    }

    /**
     * Método encargado de ejecutar un borrado de datos en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    override fun delete(entity: TareaEncordado): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            val tarea = manager.find(TareaEncordado::class.java, entity.id)
            tarea?.let {
                manager.remove(it)
                result = true
            }
        }
        return result
    }
}