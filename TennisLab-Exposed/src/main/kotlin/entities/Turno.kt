package entities


import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object TurnosTable : IntIdTable("TURNOS") {
    val uuid = uuid("uuid").uniqueIndex()
    val comienzo = datetime("comienzo")
    val final = datetime("final")

    val encordador = reference("usuario_uuid", UsuariosTable)
}

class TurnosDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TurnosDAO>(TurnosTable)

    var uuid by TurnosTable.uuid
    var comienzo by TurnosTable.comienzo
    var final by TurnosTable.final

    var encordador by UsuariosDAO referencedOn TurnosTable.encordador
}