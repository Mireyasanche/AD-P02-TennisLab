/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.turno

import entities.TurnosDAO
import entities.UsuariosDAO
import exceptions.TurnoException
import exceptions.UsuarioException
import exceptions.maquinas.MaquinaEncordarException
import mappers.fromTurnoDAOToTurno
import models.Turno
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TurnosRepository(
    private val turnosDAO: IntEntityClass<TurnosDAO>,
    private val usuariosDAO: IntEntityClass<UsuariosDAO>
) : ITurnosRepository {
    private val logger = KotlinLogging.logger {}


    /**
     * Método encargado de ejecutar transacción, con una consulta de los DAO en su interior, la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<Turno>, la lista de objetos encontrada en su respectiva base de datos convertido de DAO a modelo.
     */
    override fun findAll(): List<Turno> = transaction {
        logger.debug { "findAll() - buscando todos " }
        turnosDAO.all().map { it.fromTurnoDAOToTurno() }
    }

    /**
     * Método encargado de ejecutar transacción, con una consulta del DAO en su interior, la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar.
     *
     * @throws TurnoException si la maquina no se encuentra.
     *
     * @return Turno, el objeto que tiene el identificador introducido convertido de DAO a modelo.
     */
    override fun findById(id: Int): Turno = transaction {
        logger.debug { "findById($id) - buscando $id" }
        turnosDAO.findById(id)
            ?.fromTurnoDAOToTurno() ?: throw TurnoException("Turno no encontrado con id: $id")
    }

    /**
     * Método encargado de ejecutar transacción, con una inserción del DAO a la base de datos en su interior.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Turno, el objeto que ha sido insertado o actualizado convertido de DAO a modelo.
     */
    override fun save(entity: Turno): Turno = transaction {
        val existe = turnosDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Método encargado de ejecutar transacción, con un borrado del DAO de la base de datos en su interior.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar convertido de DAO a modelo.
     */
    override fun delete(entity: Turno): Boolean = transaction {
        val existe = turnosDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

    /**
     * Método encargado de realizar la inserción del DAO en caso de que la entidad a introducir no exista en la base de datos.
     *
     * @param entity objeto a insetar.
     *
     * @return Turno, el objeto que ha sido insertado convertido de DAO a modelo.
     */
    private fun insert(entity: Turno): Turno {

        logger.debug { "save($entity) - creando" }
        return turnosDAO.new(entity.id) {
            uuid = entity.uuid
            comienzo = entity.comienzo
            final = entity.final
            encordador = usuariosDAO.findById(entity.encordador.id)
                ?: throw UsuarioException("El encordador con id: $id no existe.")
        }.fromTurnoDAOToTurno()
    }

    /**
     * Método encargado de realizar la actualización del DAO en caso de que la entidad a introducir exista en la base de datos.
     *
     * @param entity objeto a actualizar.
     * @param existe objeto DAO si existe, nulo si no.
     *
     * @return Turno, el objeto que ha sido actualizado convertido de DAO a modelo.
     */
    private fun update(entity: Turno, existe: TurnosDAO): Turno {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            comienzo = entity.comienzo
            final = entity.final
            encordador = usuariosDAO.findById(entity.encordador.id)
                ?: throw UsuarioException("El encordador con id: $id no existe.")
        }.fromTurnoDAOToTurno()
    }
}