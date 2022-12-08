/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package controllers

import models.Usuario
import repositories.usuario.UsuariosRepository

class UsuariosController(
    private val usuariosRepository: UsuariosRepository = UsuariosRepository()
) {

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<Usuario>, la lista de objetos encontrada en su respectiva base de datoa.
     */
    fun getUsuarios(): List<Usuario> {
        return usuariosRepository.findAll()
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar
     *
     * @return Usuario?, el objeto que tiene el identificador introducido, si no se encuentra devolverá nulo.
     */
    fun getUsuarioById(id: Int): Usuario? {
        return usuariosRepository.findById(id)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir la inserción de datos
     * en la base de datos. Si existe el objeto a insertar, lo actualizará.
     * En caso contrario simplemente hará la inserción de un nuevo objeto.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return Usuario, el objeto que ha sido insertado o actualizado.
     */
    fun saveUsuario(usuario: Usuario): Usuario {
        return usuariosRepository.save(usuario)
    }

    /**
     * Método encargado de utilizar el repositorio como intermediario para conseguir un borrado de datos
     * en la base de datos.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar.
     */
    fun deleteUsuario(usuario: Usuario): Boolean {
        return usuariosRepository.delete(usuario)
    }
}