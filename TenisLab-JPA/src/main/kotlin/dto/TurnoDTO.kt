/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

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

/**
 * Esta función de extensión de Turno se ocupa de convertir el objeto de tipos transferencia de datos (DTO) para poder
 * pasar la información del mismo a ficheros de una forma más sencilla, evitando los tipos complejos.
 *
 * @return TurnoDTO, el objeto convertido en DTO.
 */
fun Turno.toDTO(): TurnoDTO {
    return TurnoDTO(
        id = id,
        uuid = uuid.toString(),
        comienzo = comienzo.toString(),
        final = final.toString(),
        encordador = encordador.toString()
    )
}