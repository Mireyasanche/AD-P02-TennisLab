/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package entities

import models.TipoUsuario
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object UsuariosTable : IntIdTable("USUARIOS") {
    val uuid = uuid("uuid").uniqueIndex()
    val nombre = varchar("nombre", 50)
    val apellido = varchar("apellido", 50)
    val email = varchar("email", 50)
    val contrasena = varchar("contrasena", 255)
    val perfil = enumeration<TipoUsuario>("perfil")
}

class UsuariosDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UsuariosDAO>(UsuariosTable)

    var uuid by UsuariosTable.uuid
    var nombre by UsuariosTable.nombre
    var apellido by UsuariosTable.apellido
    var email by UsuariosTable.email
    var contrasena by UsuariosTable.contrasena
    var perfil by UsuariosTable.perfil
}