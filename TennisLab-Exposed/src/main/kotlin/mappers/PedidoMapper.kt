/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package mappers

import entities.PedidosDAO
import models.Pedido
import models.TipoEstado
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Esta función de extensión de PedidosDAO se ocupa de convertir el objeto de acceso de datos (DAO)
 * a su tipo original, como estructura en su modelo.
 *
 * @return Pedido, el objeto convertido a modelo.
 */
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
