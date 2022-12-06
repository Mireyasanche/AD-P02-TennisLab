package models.tareas

import java.util.*

interface Tarea{
    val id: Int
    val uuid: UUID
    val precio: Float
    val pedido : UUID
}