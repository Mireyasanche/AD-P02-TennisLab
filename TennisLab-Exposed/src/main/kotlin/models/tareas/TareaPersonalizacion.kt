package models.tareas

import java.util.*

class TareaPersonalizacion(
    override val id: Int,
    override val uuid: UUID,
    override val precio: Float,
    override val pedido: UUID,
    val peso: Float,
    val balance: Float,
    val rigidez: Float
): Tarea
