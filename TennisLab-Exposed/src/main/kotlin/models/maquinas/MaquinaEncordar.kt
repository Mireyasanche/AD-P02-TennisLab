package models.maquinas

import java.time.LocalDate
import java.util.*

class MaquinaEncordar(
    override val id: Int,
    override val uuid: UUID,
    override val marca: String,
    override val modelo: String,
    override val fechaAdquisicion: LocalDate,
    override val numeroSerie: Int,
    override val turno: UUID,
    val tipo: TipoEncordaje,
    val tensionMaxima: Float,
    val tensionMinima: Float,


    ) : Maquina

enum class TipoEncordaje(valor: String) {
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