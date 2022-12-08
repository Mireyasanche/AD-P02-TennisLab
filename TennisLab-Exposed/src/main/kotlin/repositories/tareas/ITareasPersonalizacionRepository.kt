/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.tareas


import models.tareas.TareaPersonalizacion
import repositories.ICRUDRepository

interface ITareasPersonalizacionRepository : ICRUDRepository<TareaPersonalizacion, Int> {
}