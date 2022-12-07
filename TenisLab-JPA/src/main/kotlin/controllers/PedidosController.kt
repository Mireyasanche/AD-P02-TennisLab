package controllers

import models.Pedido

import repositories.pedido.PedidosRepository

class PedidosController(
    private val pedidosRepository: PedidosRepository = PedidosRepository(
    )
) {
    fun getPedidos(): List<Pedido> {
        return pedidosRepository.findAll()
    }

    fun getPedido(id: Int): Pedido? {
        return pedidosRepository.findById(id)
    }

    fun savePedido(entity: Pedido): Pedido {
        return pedidosRepository.save(entity)
    }

    fun deletePedido(entity: Pedido): Boolean {
        return pedidosRepository.delete(entity)
    }
}