/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package controllers

import models.maquinas.MaquinaEncordar
import models.maquinas.MaquinaPersonalizar
import repositories.maquinas.MaquinasEncordarRepository
import repositories.maquinas.MaquinasPersonalizarRepository

class MaquinasController(
    private val maquinasEncordarRepository: MaquinasEncordarRepository = MaquinasEncordarRepository(),
    private val maquinasPersonalizarRepository: MaquinasPersonalizarRepository = MaquinasPersonalizarRepository()

){

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<MaquinaEncordar>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    fun getMaquinasEncordar(): List<MaquinaEncordar> {
        return maquinasEncordarRepository.findAll()
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return MaquinaEncordar?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    fun getMaquinaEncordar(id: Int): MaquinaEncordar? {
        return maquinasEncordarRepository.findById(id)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir la inserción de datos
     * en la base de datos. Si existe el objeto a insertar, lo actualizará.
     * En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return MaquinaEncordar, el objeto que ha sido insertado o actualizado.
     */
    fun saveMaquinaEncordar(entity: MaquinaEncordar): MaquinaEncordar {
        return maquinasEncordarRepository.save(entity)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir un borrado de datos
     * en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    fun deleteMaquinaEncordar(entity: MaquinaEncordar): Boolean {
        return maquinasEncordarRepository.delete(entity)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<MaquinaPersonalizar>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    fun getMaquinasPersonalizar(): List<MaquinaPersonalizar> {
        return maquinasPersonalizarRepository.findAll()
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return MaquinaPersonalizar?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    fun getMaquinaPersonalizar(id: Int): MaquinaPersonalizar? {
        return maquinasPersonalizarRepository.findById(id)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir la inserción de datos
     * en la base de datos. Si existe el objeto a insertar, lo actualizará.
     * En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return MaquinaPersonalizar, el objeto que ha sido insertado o actualizado.
     */
    fun saveMaquinaPersonalizar(entity: MaquinaPersonalizar): MaquinaPersonalizar {
        return maquinasPersonalizarRepository.save(entity)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir un borrado de datos
     * en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    fun deleteMaquinaPersonalizar(entity: MaquinaPersonalizar): Boolean {
        return maquinasPersonalizarRepository.delete(entity)
    }

}