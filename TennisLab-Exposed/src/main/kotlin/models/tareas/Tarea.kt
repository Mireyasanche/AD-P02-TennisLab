package models.tareas

import models.Pedido
import java.util.*

interface Tarea {
    val id: Int
    val uuid: UUID
    val precio: Float
    val pedido: Pedido
}