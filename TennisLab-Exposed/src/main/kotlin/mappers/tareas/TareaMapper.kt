/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package mappers.tareas

import entities.tareas.TareasEncordadoDAO
import entities.tareas.TareasPersonalizacionDAO
import mappers.fromPedidosDAOToPedidos
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado
import models.tareas.TareaPersonalizacion


/**
 * Esta función de extensión de TareasEncordadoDAO se ocupa de convertir el objeto de acceso de datos (DAO)
 * a su tipo original, como estructura en su modelo.
 *
 * @return TareaEncordado, el objeto convertido a modelo.
 */
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
        pedido = pedido.fromPedidosDAOToPedidos(),
        nudos = NumeroNudos.from(nudos)
    )
}


/**
 * Esta función de extensión de TareasPersonalizacionDAO se ocupa de convertir el objeto de acceso de datos (DAO)
 * a su tipo original, como estructura en su modelo.
 *
 * @return TareaPersonalizacion, el objeto convertido a modelo.
 */
fun TareasPersonalizacionDAO.fromTareasPersonalizacionDAOToTareasPersonalizacion()
        : TareaPersonalizacion {
    return TareaPersonalizacion(
        id = id.value,
        uuid = uuid,
        precio = precio,
        peso = peso,
        balance = balance,
        rigidez = rigidez,
        pedido = pedido.fromPedidosDAOToPedidos()
    )
}