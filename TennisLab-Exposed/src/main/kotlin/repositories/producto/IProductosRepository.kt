/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.producto

import models.Producto
import repositories.ICRUDRepository
import java.util.*

interface IProductosRepository: ICRUDRepository<Producto, Int> {
}