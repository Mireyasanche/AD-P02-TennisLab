package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Turno")
data class TurnoDTO(
    val comienzo : String,
    val final : String,
    val maquina: String,
    val encordador: String
)