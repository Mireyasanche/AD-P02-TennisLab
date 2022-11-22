package models

import java.time.LocalDate
import java.util.*

class MaquinaPersonalizar(
    id: Int,
    uuid: UUID,
    marca: String,
    modelo: String,
    fechaAdquisicion: LocalDate,
    numeroSerie: Int,
    val mideManiobrabilidad: Boolean,
    val balance: Float,
    val rigidez: Float

): Maquina(
    id,
    uuid,
    marca,
    modelo,
    fechaAdquisicion,
    numeroSerie) {
}