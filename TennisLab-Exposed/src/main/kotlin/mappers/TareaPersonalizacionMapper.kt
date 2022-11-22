package mappers

import entities.TareasPersonalizacionDAO
import models.TareaPersonalizacion

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