package repositories.usuario

import entities.UsuariosDAO
import exceptions.UsuarioException
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

    override fun findAll(): List<Usuario> = transaction {
        logger.debug { "findAll() - buscando todos " }
        usuariosDAO.all().map { it.fromUsuarioDAOToUsuario() }
    }

    override fun findById(id: Int): Usuario = transaction {
        logger.debug { "findById($id) - buscando $id" }
        usuariosDAO.findById(id)
            ?.fromUsuarioDAOToUsuario() ?: throw UsuarioException("Usuario no encontrado con id: $id")
    }

    override fun save(entity: Usuario): Usuario = transaction {
        val existe = usuariosDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    override fun delete(entity: Usuario): Boolean = transaction {
        val existe = usuariosDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

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

