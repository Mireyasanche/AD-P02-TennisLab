/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package repositories.usuario

import db.HibernateManager
import db.HibernateManager.manager
import models.Usuario
import mu.KotlinLogging
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class UsuariosRepository: IUsuariosRepository {
    /**
     * Método encargado de ejecutar una NamedQuery anteriormente nombrada en su respectivo modelo y la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<Usuario>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    override fun findAll(): List<Usuario> {
        logger.debug { "findAll()" }
        var usuarios = mutableListOf<Usuario>()
        HibernateManager.query {
            val query: TypedQuery<Usuario> = manager.createNamedQuery("Usuario.findAll", Usuario::class.java)
            usuarios = query.resultList
        }
        return usuarios
    }

    /**
     * Método encargado de ejecutar una consulta la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return Usuario?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    override fun findById(id: Int): Usuario? {
        logger.debug { "findById($id)" }
        var usuario: Usuario? = null
        HibernateManager.query {
            usuario = manager.find(Usuario::class.java, id)
        }
        return usuario
    }

    /**
     * Método encargado de ejecutar una inserción de datos en la base de datos.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Usuario, el objeto que ha sido insertado o actualizado.
     */
    override fun save(entity: Usuario): Usuario {
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
    override fun delete(entity: Usuario): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            val usuario = manager.find(Usuario::class.java, entity.id)
            usuario?.let {
                manager.remove(it)
                result = true
            }
        }
        return result
    }
}