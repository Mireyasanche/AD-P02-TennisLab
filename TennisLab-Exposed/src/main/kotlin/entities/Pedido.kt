package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

object PedidosTable : IntIdTable("PEDIDOS") {
    val uuid = uuid("uuid").uniqueIndex()
    val productos = reference("productos_id", ProductosTable)
    val estado = varchar("estado", 50)
    val encordador = reference("encordador_id", UsuariosTable)
    val fechaTope = date("fecha_tope")
    val fechaEntrada = date("fecha_entrada")
    val fechaProgramada = date("fecha_programada")
    val fechaEntrega = date("fecha_entrega")
    val precio = float("precio")
}

class PedidosDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PedidosDAO>(PedidosTable)
    var uuid by PedidosTable.uuid
    var productos by ProductosDAO referencedOn ProductosTable.id
    var estado by PedidosTable.estado
    var encordador by UsuariosDAO referencedOn UsuariosTable.id
    var fechaTope by PedidosTable.fechaTope
    var fechaEntrada by PedidosTable.fechaEntrada
    var fechaProgramada by PedidosTable.fechaProgramada
    var fechaEntrega by PedidosTable.fechaEntrega
    var precio by PedidosTable.precio
}