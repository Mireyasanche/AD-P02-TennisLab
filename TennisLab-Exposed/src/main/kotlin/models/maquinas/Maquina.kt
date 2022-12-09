/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package models.maquinas

import models.Turno
import java.time.LocalDate
import java.util.*

interface Maquina {
    val id: Int
    val uuid: UUID
    val marca: String
    val modelo: String
    val fechaAdquisicion: LocalDate
    val numeroSerie: Int
    val turno: Turno
}