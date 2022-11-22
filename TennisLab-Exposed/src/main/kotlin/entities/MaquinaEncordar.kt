package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

object MaquinasEncordarTable : UUIDTable("MAQUINAS_ENCORDAR") {
    val marca = reference("marca", MaquinasTable )
    val modelo = reference("modelo",MaquinasTable)
    val fechaAdquisicion = reference("fechaAdquisicion", MaquinasTable)
    val numeroSerie = reference("numeroSerie", MaquinasTable)
    val tipo = varchar("tipo" , 10)
    val tensionMaxima = float("tensionMaxima")
    val tensionMinima = float("tensionMinima")
}

class MaquinasEncordarDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MaquinasEncordarDAO>(MaquinasEncordarTable)
    var marca by MaquinasDAO referencedOn MaquinasTable.marca
    var modelo by MaquinasDAO referencedOn MaquinasTable.modelo
    var fechaAdquisicion by MaquinasDAO referencedOn MaquinasTable.fechaAdquisicion
    var numeroSerie by MaquinasDAO referencedOn MaquinasTable.numeroSerie
    var tipo by MaquinasEncordarTable.tipo
    var tensionMaxima by MaquinasEncordarTable.tensionMaxima
    var tensionMinima by MaquinasEncordarTable.tensionMinima
}