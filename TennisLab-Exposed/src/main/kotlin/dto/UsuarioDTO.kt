/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.Producto
import models.Usuario

@Serializable
@SerialName("Usuario")
data class UsuarioDTO(
    val id : Int,
    val uuid: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasena: String,
    val perfil: String
)

/**
 * Esta función de extensión de Usuario se ocupa de convertir el objeto de tipos transferencia de datos (DTO) para poder
 * pasar la información del mismo a ficheros de una forma más sencilla, evitando los tipos complejos.
 *
 * @return UsuarioDTO, el objeto convertido en DTO.
 */
fun Usuario.toDTO(): UsuarioDTO {
    return UsuarioDTO(
        id = id,
        uuid = uuid.toString(),
        nombre = nombre,
        apellido = apellido,
        email = email,
        contrasena = contrasena,
        perfil = perfil.toString()
    )
}
