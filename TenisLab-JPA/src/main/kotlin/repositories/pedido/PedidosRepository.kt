/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package repositories.pedido

import models.Pedido
import mu.KotlinLogging
import db.HibernateManager
import db.HibernateManager.manager
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class PedidosRepository: IPedidosRepository {
    /**
     * Método encargado de ejecutar una NamedQuery anteriormente nombrada en su respectivo modelo y la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<Pedido>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    override fun findAll(): List<Pedido> {
        logger.debug { "findAll()" }
        var pedidos = mutableListOf<Pedido>()
        HibernateManager.query {
            val query: TypedQuery<Pedido> = manager.createNamedQuery("Pedido.findAll", Pedido::class.java)
            pedidos = query.resultList
        }
        return pedidos
    }

    /**
     * Método encargado de ejecutar una consulta la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return Pedido?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    override fun findById(id: Int): Pedido? {
        logger.debug { "findById($id)" }
        var pedido: Pedido? = null
        HibernateManager.query {
            pedido = manager.find(Pedido::class.java, id)
        }
        return pedido
    }

    /**
     * Método encargado de ejecutar una inserción de datos en la base de datos.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     * Ademas será necesario que el objeto en cuestión cuente con un encordador que no esté asignado ya en otros dos pedidos
     * para poder añadirse sin problemas.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Pedido, el objeto que ha sido insertado o actualizado.
     */
    override fun save(entity: Pedido): Pedido {
        require(findAll()
            .filter { it.encordador.id == entity.encordador.id }
            .map { it.encordador }
            .count() < 2 ) {"Este pedido no ha podido guardarse correctamente ya que su encordador asignado ya tiene dos pedidos asignados."}

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
    override fun delete(entity: Pedido): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            val pedido = manager.find(Pedido::class.java, entity.id)
            pedido?.let {
                manager.remove(it)
                result = true
            }
        }
        return result
    }
}