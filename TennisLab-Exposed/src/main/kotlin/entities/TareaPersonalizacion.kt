package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object TareasPersonalizacionTable: UUIDTable("TAREAS_PERSONALIZACION") {
    val precio = reference("precio", TareasTable)
    val peso = float("peso")
    val balance = float("balance")
    val rigidez = float("rigidez")
}

class TareasPersonalizacionDAO(id: EntityID<UUID>): UUIDEntity(id) {

    companion object : UUIDEntityClass<TareasEncordadoDAO>(TareasPersonalizacionTable)
    var precio by TareasDAO referencedOn TareasPersonalizacionTable.precio
    var peso by TareasPersonalizacionTable.peso
    var balance by TareasPersonalizacionTable.balance
    var rigidez by TareasPersonalizacionTable.rigidez
}