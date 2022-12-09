/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package controllers

import models.tareas.TareaEncordado
import models.tareas.TareaPersonalizacion
import repositories.tareas.TareasEncordadoRepository
import repositories.tareas.TareasPersonalizacionRepository

class TareasController(
    private val tareasEncordadoRepository: TareasEncordadoRepository = TareasEncordadoRepository(),
    private val tareasPersonalizacionRepository: TareasPersonalizacionRepository = TareasPersonalizacionRepository()
) {

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<TareaEncordado>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    fun getAllTareasEncordado(): List<TareaEncordado> {
        return tareasEncordadoRepository.findAll()
    }


    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return TareaEncordado?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    fun getTareaEncordadoById(id: Int): TareaEncordado? {
        return tareasEncordadoRepository.findById(id)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir la inserción de datos
     * en la base de datos. Si existe el objeto a insertar, lo actualizará.
     * En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return TareaEncordado, el objeto que ha sido insertado o actualizado.
     */
    fun saveTareaEncordado(tarea: TareaEncordado): TareaEncordado {
        return tareasEncordadoRepository.save(tarea)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir un borrado de datos
     * en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    fun deleteTareaEncordado(tarea: TareaEncordado): Boolean {
        return tareasEncordadoRepository.delete(tarea)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<TareaPersonalizacion>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    fun getAllTareasPersonalizacion(): List<TareaPersonalizacion> {
        return tareasPersonalizacionRepository.findAll()
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return TareaPersonalizacion?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    fun getTareaPersonalizacionById(id: Int): TareaPersonalizacion? {
        return tareasPersonalizacionRepository.findById(id)
    }


    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir la inserción de datos
     * en la base de datos. Si existe el objeto a insertar, lo actualizará.
     * En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return TareaPersonalizacion, el objeto que ha sido insertado o actualizado.
     */
    fun saveTareaPersonalizacion(tarea: TareaPersonalizacion): TareaPersonalizacion {
        return tareasPersonalizacionRepository.save(tarea)
    }


    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir un borrado de datos
     * en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    fun deleteTareaPersonalizacion(tarea: TareaPersonalizacion): Boolean {
        return tareasPersonalizacionRepository.delete(tarea)
    }
}