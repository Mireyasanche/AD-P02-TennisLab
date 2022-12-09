/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package mappers

import entities.UsuariosDAO
import models.Usuario


/**
 * Esta función de extensión de UsuariosDAO se ocupa de convertir el objeto de acceso de datos (DAO)
 * a su tipo original, como estructura en su modelo.
 *
 * @return Usuario, el objeto convertido a modelo.
 */
fun UsuariosDAO.fromUsuarioDAOToUsuario(): Usuario {
    return Usuario(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        contrasena = contrasena,
        perfil = perfil,
    )
}