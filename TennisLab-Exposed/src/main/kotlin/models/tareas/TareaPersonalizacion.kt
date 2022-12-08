package models.tareas

import models.Pedido
import java.util.*

data class TareaPersonalizacion(
    override val id: Int,
    override val uuid: UUID,
    override val precio: Float,
    override val pedido: Pedido,
    val peso: Float,
    val balance: Float,
    val rigidez: Float
) : Tarea
