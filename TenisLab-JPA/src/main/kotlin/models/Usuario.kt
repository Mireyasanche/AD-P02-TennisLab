package models

import java.util.*

data class Usuario(
    val id : Int,
    val uuid: UUID,
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasena: String,
    val perfil: TipoUsuario
    )

enum class TipoUsuario(valor: String){
    ADMINISTRADOR_ENCARGADO("ADMINISTRADOR_ENCARGADO"),
    ADMINISTRADOR_JEFE("ADMINISTRADOR_JEFE"),
    ENCORDADOR("ENCORDADOR"),
    TENISTA("TENISTA");

    companion object {
        fun from(tipoUsuario: String): TipoUsuario {
            return when (tipoUsuario.uppercase()) {
                "ADMINISTRADOR_ENCARGADO" -> ADMINISTRADOR_ENCARGADO
                "ADMINISTRADOR_JEFE" -> ADMINISTRADOR_JEFE
                "ENCORDADOR" -> ENCORDADOR
                "TENISTA" -> TENISTA
                else -> throw IllegalArgumentException("TipoUsuario no reconocido")
            }
        }
    }
}
