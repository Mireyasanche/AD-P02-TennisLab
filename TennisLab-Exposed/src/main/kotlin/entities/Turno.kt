package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*

object TurnosTable : UUIDTable("TURNOS") {
    val comienzo = datetime("comienzo")
    val final = datetime("final")
    val maquina = reference("maquina_id", MaquinasTable)
    val encordador = reference("usuario_id", UsuariosTable)
}

class TurnosDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TurnosDAO>(TurnosTable)
    var comienzo by TurnosTable.comienzo
    var final by TurnosTable.final
    var maquina by MaquinasDAO referencedOn MaquinasTable.id
    var encordador by UsuariosDAO referencedOn UsuariosTable.id
}