package models

import java.util.*

class TareaPersonalizacion(
    id: Int,
    uuid: UUID,
    precio: Float,
    peso: Float,
    balance: Float,
    rigidez: Float
): Tarea(id, uuid, precio)
