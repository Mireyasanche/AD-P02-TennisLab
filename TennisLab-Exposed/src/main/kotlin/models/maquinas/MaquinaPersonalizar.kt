package models.maquinas

import models.Turno
import java.time.LocalDate
import java.util.*

class MaquinaPersonalizar(
    override val id: Int,
    override val uuid: UUID,
    override val marca: String,
    override val modelo: String,
    override val fechaAdquisicion: LocalDate,
    override val numeroSerie: Int,
    override val turno: Turno,
    val mideManiobrabilidad: Boolean,
    val balance: Float,
    val rigidez: Float

) : Maquina