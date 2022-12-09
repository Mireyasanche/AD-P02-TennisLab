/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

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
    @Column(name = "fecha_adquision")
    @Type(type = "org.hibernate.type.LocalDateType")
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
    /**
     * Función que parsea los Enums del objeto MaquinaEncordado. Indicando su equivalencia al valor de tipo String.
     *
     * @param tipoEncordaje El enum en formato String equivalente al valor de la clase Enum.
     *
     * @throws IllegalArgumentException Excepción que el método lanzará si el String que trata de parsear no eixste, o el tipo no existe.
     *
     * @return TipoEncordaje, un enum que variará dependiendo del valor de dicha variable.
     */
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