package mappers.tareas

import entities.tareas.TareasDAO
import entities.tareas.TareasEncordadoTable.cordajeHorizontal
import entities.tareas.TareasEncordadoTable.cordajeVertical
import entities.tareas.TareasEncordadoTable.nudos
import entities.tareas.TareasEncordadoTable.tensionHorizontal
import entities.tareas.TareasEncordadoTable.tensionVertical
import entities.tareas.TareasPersonalizacionTable.balance
import entities.tareas.TareasPersonalizacionTable.peso
import entities.tareas.TareasPersonalizacionTable.rigidez
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado
import models.tareas.TareaPersonalizacion

fun TareasDAO.fromTareasEncordadoDAOToTareaEncordado()
: TareaEncordado {
    return TareaEncordado(
        id = 0,
        uuid = id.value,
        // TODO: revisar referencia de precio.
        precio = precio,
        tensionHorizontal = tensionHorizontal.toString().toFloat(),
        cordajeHorizontal = cordajeHorizontal.toString(),
        tensionVertical = tensionVertical.toString().toFloat(),
        cordajeVertical = cordajeVertical.toString(),
        nudos = NumeroNudos.from(nudos.toString())
    )
}

fun TareasDAO.fromTareasPersonalizacionDAOToTareasPersonalizacion()
: TareaPersonalizacion {
    return TareaPersonalizacion(
        id = 0,
        uuid = id.value,
        // TODO: revisar referencia de precio.
        precio = precio,
        peso = peso.toString().toFloat(),
        balance = balance.toString().toFloat(),
        rigidez = rigidez.toString().toFloat()
    )
}