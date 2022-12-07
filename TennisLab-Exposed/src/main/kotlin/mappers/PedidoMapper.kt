package mappers

import entities.PedidosDAO
import models.Pedido
import models.TipoEstado
import org.jetbrains.exposed.dao.id.EntityID

fun PedidosDAO.fromPedidosDAOToPedidos(): Pedido {
    return Pedido(
        id = id.value,
        uuid = uuid,
        estado = TipoEstado.from(estado),
        encordador = encordador.fromUsuarioDAOToUsuario(),
        fechaTope = fechaTope,
        fechaEntrada = fechaEntrada,
        fechaProgramada = fechaProgramada,
        fechaEntrega = fechaEntrega,
        precio = precio
    )
}
