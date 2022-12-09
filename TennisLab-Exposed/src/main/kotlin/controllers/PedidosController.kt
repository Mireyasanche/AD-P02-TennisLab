/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package controllers

import entities.PedidosDAO
import entities.UsuariosDAO
import models.Pedido

import repositories.pedido.PedidosRepository

class PedidosController(
    private val pedidosRepository: PedidosRepository = PedidosRepository(
        PedidosDAO,
        UsuariosDAO
    )
) {

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<Pedido>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    fun getPedidos(): List<Pedido> {
        return pedidosRepository.findAll()
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return Pedido?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    fun getPedido(id: Int): Pedido {
        return pedidosRepository.findById(id)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir la inserción de datos
     * en la base de datos. Si existe el objeto a insertar, lo actualizará.
     * En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Pedido, el objeto que ha sido insertado o actualizado.
     */
    fun savePedido(entity: Pedido): Pedido {
        return pedidosRepository.save(entity)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir un borrado de datos
     * en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Pedido, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    fun deletePedido(entity: Pedido): Boolean {
        return pedidosRepository.delete(entity)
    }
}