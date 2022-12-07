package controllers

import models.tareas.TareaEncordado
import models.tareas.TareaPersonalizacion
import repositories.tareas.TareasEncordadoRepository
import repositories.tareas.TareasPersonalizacionRepository

class TareasController(
    private val tareasEncordadoRepository: TareasEncordadoRepository = TareasEncordadoRepository(),
    private val tareasPersonalizacionRepository: TareasPersonalizacionRepository = TareasPersonalizacionRepository()
) {
    fun getAllTareasEncordado(): List<TareaEncordado> {
        return tareasEncordadoRepository.findAll()
    }

    fun getTareaEncordadoById(id: Int): TareaEncordado? {
        return tareasEncordadoRepository.findById(id)
    }

    fun saveTareaEncordado(tarea: TareaEncordado): TareaEncordado {
        return tareasEncordadoRepository.save(tarea)
    }

    fun deleteTareaEncordado(tarea: TareaEncordado): Boolean {
        return tareasEncordadoRepository.delete(tarea)
    }

    fun getAllTareasPersonalizacion(): List<TareaPersonalizacion> {
        return tareasPersonalizacionRepository.findAll()
    }

    fun getTareaPersonalizacionById(id: Int): TareaPersonalizacion? {
        return tareasPersonalizacionRepository.findById(id)
    }

    fun saveTareaPersonalizacion(tarea: TareaPersonalizacion): TareaPersonalizacion {
        return tareasPersonalizacionRepository.save(tarea)
    }

    fun deleteTareaPersonalizacion(tarea: TareaPersonalizacion): Boolean {
        return tareasPersonalizacionRepository.delete(tarea)
    }
}