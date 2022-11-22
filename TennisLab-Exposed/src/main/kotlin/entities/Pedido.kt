package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

// TODO: revisar como meter listas en las entities.
object PedidosTable : UUIDTable("PEDIDOS") {
    val tareas = reference("tarea_id", TareasTable)
    val productos = reference("productos_id", ProductosTable)
    val estado = varchar("estado", 50)
    val encordador = reference("encordador:id", UsuariosTable)
    val fechaTope = date("fecha_tope")
    val fechaEntrada = date("fecha_entrada")
    val fechaProgramada = date("fecha_programada")
    val fechaEntrega = date("fecha_entrega")
    val precio = float("precio")
}

class PedidosDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PedidosDAO>(PedidosTable)

    var tareas by TareasDAO referencedOn TareasTable.id
    var productos by ProductosDAO referencedOn ProductosTable.id
    var estado by PedidosTable.estado
    var encordador by UsuariosDAO referencedOn UsuariosTable.id
    var fechaTope by PedidosTable.fechaTope
    var fechaEntrada by PedidosTable.fechaEntrada
    var fechaProgramada by PedidosTable.fechaProgramada
    var fechaEntrega by PedidosTable.fechaEntrega
    var precio by PedidosTable.precio
}