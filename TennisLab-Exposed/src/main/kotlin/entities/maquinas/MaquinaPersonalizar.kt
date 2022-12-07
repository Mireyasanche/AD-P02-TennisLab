package entities.maquinas

import entities.TurnosDAO
import entities.TurnosTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object MaquinasPersonalizarTable : IntIdTable("MAQUINAS_PERSONALIZAR") {
    val uuid = uuid("uuid").uniqueIndex()
    val marca = varchar("marca", 100)
    val modelo = varchar("modelo", 100)
    val fechaAdquisicion = date("fechaAdquisicion")
    val numeroSerie = integer("numeroSerie")
    val turno = reference("turno", TurnosTable)
    val mideManiobrabilidad = bool("mideManiobrabilidad")
    val balance = float("balance")
    val rigidez = float("rigidez")
}

class MaquinasPersonalizarDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MaquinasPersonalizarDAO>(MaquinasPersonalizarTable)

    var uuid by MaquinasPersonalizarTable.uuid
    var marca by MaquinasPersonalizarTable.marca
    var modelo by MaquinasPersonalizarTable.modelo
    var fechaAdquisicion by MaquinasPersonalizarTable.fechaAdquisicion
    var numeroSerie by MaquinasPersonalizarTable.numeroSerie
    var turno by TurnosDAO referencedOn MaquinasPersonalizarTable.turno
    var mideManiobrabilidad by MaquinasPersonalizarTable.mideManiobrabilidad
    var balance by MaquinasPersonalizarTable.balance
    var rigidez by MaquinasPersonalizarTable.rigidez
}