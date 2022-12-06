package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Turno")
data class TurnoDTO(
    val id: Int,
    val uuid: String,
    val comienzo: String,
    val final: String,
    val encordador: String
)