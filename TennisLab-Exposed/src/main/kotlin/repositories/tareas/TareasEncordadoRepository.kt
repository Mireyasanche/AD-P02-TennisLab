/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.tareas

import entities.PedidosDAO
import entities.tareas.TareasEncordadoDAO
import exceptions.maquinas.MaquinaEncordarException
import exceptions.tareas.TareaEncordadoException
import mappers.tareas.fromTareasEncordadoDAOToTareasEncordado
import models.tareas.TareaEncordado
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TareasEncordadoRepository(
    private val tareasDAO: IntEntityClass<TareasEncordadoDAO>,
    private val pedidosDAO: IntEntityClass<PedidosDAO>
) : ITareasEncordadoRepository {

    private val logger = KotlinLogging.logger { }

    /**
     * Método encargado de ejecutar transacción, con una consulta de los DAO en su interior, la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<TareaEncordado>, la lista de objetos encontrada en su respectiva base de datos convertido de DAO a modelo.
     */
    override fun findAll(): List<TareaEncordado> = transaction {
        logger.debug { "findAll() - buscando todos" }
        tareasDAO.all().map { it.fromTareasEncordadoDAOToTareasEncordado() }
    }

    /**
     * Método encargado de ejecutar transacción, con una consulta del DAO en su interior, la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar.
     *
     * @throws TareaEncordadoException si la maquina no se encuentra.
     *
     * @return TareaEncordado, el objeto que tiene el identificador introducido convertido de DAO a modelo.
     */
    override fun findById(id: Int): TareaEncordado {
        logger.debug { "findById($id) - buscando $id" }
        return tareasDAO.findById(id)
            ?.fromTareasEncordadoDAOToTareasEncordado()
            ?: throw TareaEncordadoException("Tarea no encontrada con id $id")
    }

    /**
     * Método encargado de ejecutar transacción, con una inserción del DAO a la base de datos en su interior.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return TareaEncordado, el objeto que ha sido insertado o actualizado convertido de DAO a modelo.
     */
    override fun save(entity: TareaEncordado): TareaEncordado = transaction {
        val existe = tareasDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Método encargado de realizar la actualización del DAO en caso de que la entidad a introducir exista en la base de datos.
     *
     * @param entity objeto a actualizar.
     * @param existe objeto DAO si existe, nulo si no.
     *
     * @return TareaEncordado, el objeto que ha sido actualizado convertido de DAO a modelo.
     */
    private fun update(entity: TareaEncordado, existe: TareasEncordadoDAO): TareaEncordado {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            pedido = pedidosDAO.findById(entity.pedido.id)!!
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeVertical = entity.cordajeVertical
            cordajeHorizontal = entity.cordajeHorizontal
            nudos = entity.nudos.toString()
            precio = entity.precio
        }.fromTareasEncordadoDAOToTareasEncordado()
    }

    /**
     * Método encargado de realizar la inserción del DAO en caso de que la entidad a introducir no exista en la base de datos.
     *
     * @param entity objeto a insetar.
     *
     * @return TareaEncordado, el objeto que ha sido insertado convertido de DAO a modelo.
     */
    private fun insert(entity: TareaEncordado): TareaEncordado {
        logger.debug { "save($entity) - creando" }
        return tareasDAO.new(entity.id) {
            uuid = entity.uuid
            pedido = pedidosDAO.findById(entity.pedido.id)!!
            tensionHorizontal = entity.tensionHorizontal
            tensionVertical = entity.tensionVertical
            cordajeVertical = entity.cordajeVertical
            cordajeHorizontal = entity.cordajeHorizontal
            nudos = entity.nudos.toString()
            precio = entity.precio
        }.fromTareasEncordadoDAOToTareasEncordado()
    }

    /**
     * Método encargado de ejecutar transacción, con un borrado del DAO de la base de datos en su interior.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar convertido de DAO a modelo.
     */
    override fun delete(entity: TareaEncordado): Boolean = transaction {
        val existe = tareasDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}