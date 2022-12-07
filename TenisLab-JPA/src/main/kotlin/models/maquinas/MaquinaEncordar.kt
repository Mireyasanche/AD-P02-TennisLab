package models.maquinas

import models.Turno
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "MAQUINAS_ENCORDAR")
@NamedQuery(name = "MaquinaEncordar.findAll", query = "SELECT m FROM MaquinaEncordar m")
data class MaquinaEncordar(
    @Id
    override val id: Int,

    @Column(name = "uuid")
    @Type(type = "uuid-char")
    override val uuid: UUID,
    override val marca: String,
    override val modelo: String,
    override val fechaAdquisicion: LocalDate,
    override val numeroSerie: Int,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "turno_id", referencedColumnName = "id", nullable = false)
    override val turno: Turno,

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