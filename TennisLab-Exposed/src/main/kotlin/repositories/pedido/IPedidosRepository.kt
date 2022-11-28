package repositories.pedido

import models.Pedido
import repositories.ICRUDRepository
import java.util.*

interface IPedidosRepository: ICRUDRepository<Pedido, UUID> {
}