package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
