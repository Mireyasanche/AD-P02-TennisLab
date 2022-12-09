/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package models

import java.time.LocalDateTime
import java.util.*

data class Turno(
    val id: Int,
    val uuid: UUID,
    val comienzo: LocalDateTime,
    val final: LocalDateTime,
    var encordador: Usuario
)