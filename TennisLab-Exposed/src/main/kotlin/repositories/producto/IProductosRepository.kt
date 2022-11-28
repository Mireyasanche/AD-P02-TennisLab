package repositories.producto

import models.Producto
import repositories.ICRUDRepository
import java.util.*

interface IProductosRepository: ICRUDRepository<Producto, UUID> {
}