package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*
import javax.swing.text.html.parser.Entity

object TareasEncordadoTable: UUIDTable() {
    val precio = reference("precio", TareasTable)
    val tensionHorizontal = float("tension_horizontal")
    val cordajeHorizontal = varchar("cordaje_horizontal", 255)
    val tensionVertical = float("tension_vertical")
    val cordajeVertical = varchar("cordaje_vertical", 255)
    val nudos = varchar("nudos", 50)
}

class TareasEncordadoDAO(id: EntityID<UUID>): UUIDEntity(id) {

    companion object : UUIDEntityClass<TareasEncordadoDAO>(TareasEncordadoTable)
    var precio by TareasDAO referencedOn TareasEncordadoTable.precio
    var tensionHorizontal by TareasEncordadoTable.tensionHorizontal
    var cordajeHorizontal by TareasEncordadoTable.cordajeHorizontal
    var tensionVertical by TareasEncordadoTable.tensionVertical
    var cordajeVertical by TareasEncordadoTable.cordajeVertical
    var nudos by TareasEncordadoTable.nudos
}