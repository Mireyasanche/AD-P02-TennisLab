/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package repositories.turno

import models.Turno
import repositories.ICRUDRepository

interface ITurnosRepository : ICRUDRepository<Turno, Int> {
}