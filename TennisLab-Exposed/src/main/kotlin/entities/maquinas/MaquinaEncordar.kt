/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package entities.maquinas

import entities.TurnosDAO
import entities.TurnosTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object MaquinasEncordarTable : IntIdTable("MAQUINAS_ENCORDAR") {
    val uuid = uuid("uuid").uniqueIndex()
    val marca = varchar("marca", 100 )
    val modelo = varchar("modelo", 100)
    val fechaAdquisicion = date("fechaAdquisicion")
    val numeroSerie = integer("numeroSerie")
    val turno = reference("turno", TurnosTable)
    val tipo = varchar("tipo" , 10)
    val tensionMaxima = float("tensionMaxima")
    val tensionMinima = float("tensionMinima")
}

class MaquinasEncordarDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MaquinasEncordarDAO>(MaquinasEncordarTable)
    var uuid by MaquinasEncordarTable.uuid
    var marca by MaquinasEncordarTable.marca
    var modelo by MaquinasEncordarTable.modelo
    var fechaAdquisicion by MaquinasEncordarTable.fechaAdquisicion
    var numeroSerie by MaquinasEncordarTable.numeroSerie
    var turno by TurnosDAO referencedOn MaquinasEncordarTable.turno
    var tipo by MaquinasEncordarTable.tipo
    var tensionMaxima by MaquinasEncordarTable.tensionMaxima
    var tensionMinima by MaquinasEncordarTable.tensionMinima
}