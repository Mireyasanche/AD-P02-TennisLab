/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.usuario

import entities.UsuariosDAO
import exceptions.UsuarioException
import exceptions.maquinas.MaquinaEncordarException
import mappers.fromUsuarioDAOToUsuario
import models.Usuario
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import services.passwordCodification

class UsuariosRepository(
    private val usuariosDAO: IntEntityClass<UsuariosDAO>
) : IUsuariosRepository {

    private val logger = KotlinLogging.logger {}

    /**
     * Método encargado de ejecutar transacción, con una consulta de los DAO en su interior, la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<Usuario>, la lista de objetos encontrada en su respectiva base de datos convertido de DAO a modelo.
     */
    override fun findAll(): List<Usuario> = transaction {
        logger.debug { "findAll() - buscando todos " }
        usuariosDAO.all().map { it.fromUsuarioDAOToUsuario() }
    }

    /**
     * Método encargado de ejecutar transacción, con una consulta del DAO en su interior, la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar.
     *
     * @throws UsuarioException si la maquina no se encuentra.
     *
     * @return Usuario, el objeto que tiene el identificador introducido convertido de DAO a modelo.
     */
    override fun findById(id: Int): Usuario = transaction {
        logger.debug { "findById($id) - buscando $id" }
        usuariosDAO.findById(id)
            ?.fromUsuarioDAOToUsuario() ?: throw UsuarioException("Usuario no encontrado con id: $id")
    }


    /**
     * Método encargado de ejecutar transacción, con una inserción del DAO a la base de datos en su interior.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Usuario, el objeto que ha sido insertado o actualizado convertido de DAO a modelo.
     */
    override fun save(entity: Usuario): Usuario = transaction {
        val existe = usuariosDAO.findById(entity.id)

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
    override fun delete(entity: Usuario): Boolean = transaction {
        val existe = usuariosDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

    /**
     * Método encargado de realizar la inserción del DAO en caso de que la entidad a introducir no exista en la base de datos.
     *
     * @param entity objeto a insetar.
     *
     * @return Usuario, el objeto que ha sido insertado convertido de DAO a modelo.
     */
    private fun insert(entity: Usuario): Usuario {
        logger.debug { "save($entity) - creando" }
        return usuariosDAO.new(entity.id) {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            contrasena = passwordCodification(entity.contrasena)
            perfil = entity.perfil
        }.fromUsuarioDAOToUsuario()
    }


    /**
     * Método encargado de realizar la actualización del DAO en caso de que la entidad a introducir exista en la base de datos.
     *
     * @param entity objeto a actualizar.
     * @param existe objeto DAO si existe, nulo si no.
     *
     * @return Usuario, el objeto que ha sido actualizado convertido de DAO a modelo.
     */
    private fun update(entity: Usuario, existe: UsuariosDAO): Usuario {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            contrasena = entity.contrasena
            perfil = entity.perfil
        }.fromUsuarioDAOToUsuario()
    }
}

