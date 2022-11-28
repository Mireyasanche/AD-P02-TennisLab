package models.maquinas

import java.time.LocalDate
import java.util.*

interface Maquina {
    val id: Int
    val uuid: UUID
    val marca: String
    val modelo: String
    val fechaAdquisicion: LocalDate
    val numeroSerie: Int
}