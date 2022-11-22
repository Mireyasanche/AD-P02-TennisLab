package models.maquinas

import java.time.LocalDate
import java.util.*

abstract class Maquina(
    val id: Int,
    val uuid: UUID,
    val marca: String,
    val modelo: String,
    val fechaAdquisicion: LocalDate,
    val numeroSerie: Int
    )