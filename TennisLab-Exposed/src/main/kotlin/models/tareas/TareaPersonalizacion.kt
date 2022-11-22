package models.tareas

import java.util.*

class TareaPersonalizacion(
    id: Int,
    uuid: UUID,
    precio: Float,
    val peso: Float,
    val balance: Float,
    val rigidez: Float
): Tarea(id, uuid, precio)
