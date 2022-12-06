package entities

import models.TipoProducto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ProductosTable : IntIdTable("PRODUCTOS") {
    val uuid = uuid("uuid").uniqueIndex()
    val tipoProducto = enumeration<TipoProducto>("tipo_producto")
    val marca = varchar("marca", 255)
    val modelo = varchar("modelo", 255)
    val precio = float("precio")
    val stock = integer("stock")
}

class ProductosDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProductosDAO>(ProductosTable)

    var uuid by ProductosTable.uuid
    var tipoProducto by ProductosTable.tipoProducto
    var marca by ProductosTable.marca
    var modelo by ProductosTable.modelo
    var precio by ProductosTable.precio
    var stock by ProductosTable.stock
}