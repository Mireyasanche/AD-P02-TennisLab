package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

object MaquinasTable : UUIDTable("MAQUINAS") {
    val marca = varchar("marca", 50)
    val modelo = varchar("modelo",  50)
    val fechaAdquisicion = date("fechaAdquisicion")
    val numeroSerie = integer("numeroSerie")
}

class MaquinasDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MaquinasDAO>(MaquinasTable)
    var marca by MaquinasTable.marca
    var modelo by MaquinasTable.modelo
    var fechaAdquisicion by MaquinasTable.fechaAdquisicion
    var numeroSerie by MaquinasTable.numeroSerie
}