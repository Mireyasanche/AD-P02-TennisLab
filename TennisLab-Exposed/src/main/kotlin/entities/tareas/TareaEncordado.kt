package entities.tareas

import entities.PedidosDAO
import entities.PedidosTable
import entities.TurnosDAO
import entities.maquinas.MaquinasEncordarTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object TareasEncordadoTable: IntIdTable("TAREAS_ENCORDADO") {
    val uuid = uuid("uuid").uniqueIndex()
    val precio = float("precio")
    val pedido = reference("pedido", PedidosTable)
    val tensionHorizontal = float("tension_horizontal")
    val cordajeHorizontal = varchar("cordaje_horizontal", 255)
    val tensionVertical = float("tension_vertical")
    val cordajeVertical = varchar("cordaje_vertical", 255)
    val nudos = varchar("nudos", 50)
}

class TareasEncordadoDAO(id: EntityID<Int>): IntEntity(id) {

    companion object : IntEntityClass<TareasEncordadoDAO>(TareasEncordadoTable)
    var uuid by TareasEncordadoTable.uuid
    var precio by TareasEncordadoTable.precio
    var pedido by PedidosDAO referencedOn TareasPersonalizacionTable.pedido
    var tensionHorizontal by TareasEncordadoTable.tensionHorizontal
    var cordajeHorizontal by TareasEncordadoTable.cordajeHorizontal
    var tensionVertical by TareasEncordadoTable.tensionVertical
    var cordajeVertical by TareasEncordadoTable.cordajeVertical
    var nudos by TareasEncordadoTable.nudos
}