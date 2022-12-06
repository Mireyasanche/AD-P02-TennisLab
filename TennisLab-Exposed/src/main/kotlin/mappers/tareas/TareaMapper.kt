package mappers.tareas

import entities.tareas.TareasEncordadoDAO
import entities.tareas.TareasPersonalizacionDAO
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado
import models.tareas.TareaPersonalizacion

fun TareasEncordadoDAO.fromTareasEncordadoDAOToTareasEncordado()
        : TareaEncordado {
    return TareaEncordado(
        id = id.value,
        uuid = uuid,
        precio = precio,
        tensionHorizontal = tensionHorizontal,
        cordajeHorizontal = cordajeHorizontal,
        tensionVertical = tensionVertical,
        cordajeVertical = cordajeVertical,
        pedido = pedido.uuid,
        nudos = NumeroNudos.from(nudos)
    )
}

fun TareasPersonalizacionDAO.fromTareasPersonalizacionDAOToTareasPersonalizacion()
        : TareaPersonalizacion {
    return TareaPersonalizacion(
        id = id.value,
        uuid = uuid,
        precio = precio,
        peso = peso,
        balance = balance,
        rigidez = rigidez,
        pedido = pedido.uuid
    )
}