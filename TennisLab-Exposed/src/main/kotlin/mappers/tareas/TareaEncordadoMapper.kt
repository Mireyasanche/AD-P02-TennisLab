package mappers.tareas

import entities.tareas.TareasEncordadoDAO
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado

fun TareasEncordadoDAO.fromTareasEncordadoDAOToTareaEncordado()
: TareaEncordado {
    return TareaEncordado(
        id = 0,
        uuid = id.value,
        // TODO: revisar referencia de precio.
        precio = precio.precio,
        tensionHorizontal = tensionHorizontal,
        cordajeHorizontal = cordajeHorizontal,
        tensionVertical = tensionVertical,
        cordajeVertical = cordajeVertical,
        nudos = NumeroNudos.from(nudos)
    )
}