package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.Turno
import models.Usuario

@Serializable
@SerialName("Turno")
data class TurnoDTO(
    val id: Int,
    val uuid: String,
    val comienzo: String,
    val final: String,
    val encordador: String
)

fun Turno.toDTO(): TurnoDTO {
    return TurnoDTO(
        id = id,
        uuid = uuid.toString(),
        comienzo = comienzo.toString(),
        final = final.toString(),
        encordador = encordador.toString()
    )
}