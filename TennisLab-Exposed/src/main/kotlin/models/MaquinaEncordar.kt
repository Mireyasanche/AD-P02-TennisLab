package models

import java.time.LocalDate
import java.util.*

class MaquinaEncordar(
    id: Int,
    uuid: UUID,
    marca: String,
    modelo: String,
    fechaAdquisicion: LocalDate,
    numeroSerie: Int,
    val tipo: TipoEncordaje,
    val tensionMaxima: Float,
    val tensionMinima: Float

):
    Maquina(
        id,
        uuid,
        marca,
        modelo,
        fechaAdquisicion,
        numeroSerie
    )

enum class TipoEncordaje(valor: String){
    MANUAL("MANUAL"),
    AUTOMATICA("AUTOMATICA");

    companion object {
        fun from(tipoEncordaje: String): TipoEncordaje {
            return when (tipoEncordaje.uppercase()) {
                "MANUAL" -> MANUAL
                "AUTOMATICA" -> AUTOMATICA
                else -> throw IllegalArgumentException("TipoEncordaje no reconocido")
            }
        }
    }
}