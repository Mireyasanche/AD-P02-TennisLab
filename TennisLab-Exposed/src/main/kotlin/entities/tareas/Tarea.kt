package entities.tareas

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object TareasTable: UUIDTable("TAREAS") {
    val precio = float("precio")
}

class TareasDAO(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<TareasDAO>(TareasTable)

    var precio by TareasTable.precio
}