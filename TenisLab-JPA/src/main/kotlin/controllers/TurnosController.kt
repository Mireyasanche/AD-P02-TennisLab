/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package controllers

import models.Turno
import repositories.turno.TurnosRepository

class TurnosController(
    private val turnosRepository: TurnosRepository = TurnosRepository()
) {

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<Turno>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    fun getTurnos(): List<Turno> {
        return turnosRepository.findAll()
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return Turno?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    fun getTurnoById(id: Int): Turno? {
        return turnosRepository.findById(id)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir la inserción de datos
     * en la base de datos. Si existe el objeto a insertar, lo actualizará.
     * En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Turno, el objeto que ha sido insertado o actualizado.
     */
    fun saveTurno(turno: Turno): Turno {
        return turnosRepository.save(turno)
    }


    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir un borrado de datos
     * en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    fun deleteTurno(turno: Turno): Boolean {
        return turnosRepository.delete(turno)
    }
}