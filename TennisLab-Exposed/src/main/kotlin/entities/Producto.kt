package entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ProductosTable: UUIDTable("PRODUCTOS") {
    val tipoProducto = varchar("tipo_producto", 50)
    val marca = varchar("marca", 255)
    val modelo = varchar("modelo", 255)
    val precio = float("precio")
    val stock = integer("stock")
}

class ProductosDAO(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<ProductosDAO>(ProductosTable)

    var tipoProducto by ProductosTable.tipoProducto
    var marca by ProductosTable.marca
    var modelo by ProductosTable.modelo
    var precio by ProductosTable.precio
    var stock by ProductosTable.stock
}