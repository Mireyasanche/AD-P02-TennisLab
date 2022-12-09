/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package entities.tareas

import entities.PedidosDAO
import entities.PedidosTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object TareasPersonalizacionTable : IntIdTable("TAREAS_PERSONALIZACION") {
    val uuid = uuid("uuid").uniqueIndex()
    val precio = float("precio")
    val pedido = reference("pedido", PedidosTable)
    val peso = float("peso")
    val balance = float("balance")
    val rigidez = float("rigidez")
}

class TareasPersonalizacionDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TareasPersonalizacionDAO>(TareasPersonalizacionTable)

    var uuid by TareasPersonalizacionTable.uuid
    var precio by TareasPersonalizacionTable.precio
    var pedido by PedidosDAO referencedOn TareasPersonalizacionTable.pedido
    var peso by TareasPersonalizacionTable.peso
    var balance by TareasPersonalizacionTable.balance
    var rigidez by TareasPersonalizacionTable.rigidez
}