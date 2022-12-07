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
