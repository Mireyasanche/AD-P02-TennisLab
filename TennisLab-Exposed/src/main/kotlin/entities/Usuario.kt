package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object UsuariosTable : UUIDTable("USUARIOS") {
    val nombre = varchar("nombre", 50)
    val apellido = varchar("apellido", 50)
    val email = varchar("email", 50)
    val contrasena = varchar("contrasena", 20)
    val perfil = varchar("perfil", 23)
}

class UsuariosDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UsuariosDAO>(UsuariosTable)
    var nombre by UsuariosTable.nombre
    var apellido by UsuariosTable.apellido
    var email by UsuariosTable.email
    var contrasena by UsuariosTable.contrasena
    var perfil by UsuariosTable.perfil

}