package repositories.tareas

import models.tareas.Tarea
import repositories.ICRUDRepository
import java.util.*

interface ITareasRepository: ICRUDRepository<Tarea, UUID> {
}