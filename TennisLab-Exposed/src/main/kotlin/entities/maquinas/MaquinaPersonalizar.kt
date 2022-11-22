package entities.maquinas

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object MaquinasPersonalizarTable : UUIDTable("MAQUINAS_PERSONALIZAR") {
    val marca = reference("marca", MaquinasTable )
    val modelo = reference("modelo", MaquinasTable)
    val fechaAdquisicion = reference("fechaAdquisicion", MaquinasTable)
    val numeroSerie = reference("numeroSerie", MaquinasTable)
    val mideManiobrabilidad = bool("mideManiobrabilidad")
    val balance = float("balance")
    val rigidez = float("rigidez")
}

class MaquinasPersonalizarDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MaquinasPersonalizarDAO>(MaquinasPersonalizarTable)
    var marca by MaquinasDAO referencedOn MaquinasTable.marca
    var modelo by MaquinasDAO referencedOn MaquinasTable.modelo
    var fechaAdquisicion by MaquinasDAO referencedOn MaquinasTable.fechaAdquisicion
    var numeroSerie by MaquinasDAO referencedOn MaquinasTable.numeroSerie
    var mideManiobrabilidad by MaquinasPersonalizarTable.mideManiobrabilidad
    var balance by MaquinasPersonalizarTable.balance
    var rigidez by MaquinasPersonalizarTable.rigidez
}