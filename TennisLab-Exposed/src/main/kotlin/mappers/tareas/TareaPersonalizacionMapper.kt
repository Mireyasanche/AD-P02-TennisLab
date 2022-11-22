package mappers.tareas

import entities.tareas.TareasPersonalizacionDAO
import models.tareas.TareaPersonalizacion

fun TareasPersonalizacionDAO.fromTareasPersonalizacionDATToTareasPersonalizacion()
: TareaPersonalizacion {
    return TareaPersonalizacion(
        id = 0,
        uuid = id.value,
        // TODO: revisar referencia de precio.
        precio = precio.precio,
        peso = peso,
        balance = balance,
        rigidez = rigidez
    )
}