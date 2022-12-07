package repositories.usuario

import db.HibernateManager
import db.HibernateManager.manager
import models.Usuario
import mu.KotlinLogging
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class UsuariosRepository: IUsuariosRepository {
    override fun findAll(): List<Usuario> {
        logger.debug { "findAll()" }
        var usuarios = mutableListOf<Usuario>()
        HibernateManager.query {
            val query: TypedQuery<Usuario> = manager.createNamedQuery("Usuario.findAll", Usuario::class.java)
            usuarios = query.resultList
        }
        return usuarios
    }

    override fun findById(id: Int): Usuario? {
        TODO("Not yet implemented")
    }

    override fun save(entity: Usuario): Usuario {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Usuario): Boolean {
        TODO("Not yet implemented")
    }
}