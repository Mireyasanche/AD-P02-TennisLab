package mappers

import entities.TareasEncordadoDAO
import models.NumeroNudos
import models.Tarea
import models.TareaEncordado

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