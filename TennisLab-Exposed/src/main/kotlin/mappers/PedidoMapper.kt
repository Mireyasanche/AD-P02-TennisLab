package mappers

import entities.PedidosDAO
import mappers.tareas.fromTareasPersonalizacionDAOToTareasPersonalizacion
import models.Pedido
import models.TipoEstado

// TODO: hacer el mapeo de PedidoDAO cuando sepamos arreglar el problema con las listas.
fun PedidosDAO.fromPedidosDAOToPedidos(): Pedido {
    return Pedido(
        id = id.value,
        uuid = uuid,
        estado = TipoEstado.from(estado),
        encordador = encordador.fromUsuarioDAOToUsuario(),
        fechaTope   = fechaTope,
        fechaEntrada = fechaEntrada,
        fechaProgramada = fechaProgramada,
        fechaEntrega= fechaEntrega,
        precio = precio
    )

}