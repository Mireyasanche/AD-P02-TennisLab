package models.tareas

import models.Pedido
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TAREAS_ENCORDADO")
@NamedQuery(name = "TareaEncordado.findAll", query = "SELECT t FROM TareaEncordado t")
data class TareaEncordado(
    @Id
    override val id: Int,

    @Column(name = "uuid")
    @Type(type = "uuid-char")
    override val uuid: UUID,

    override val precio: Float,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    override val pedido: Pedido,
    val tensionHorizontal: Float,
    val cordajeHorizontal: String,
    val tensionVertical: Float,
    val cordajeVertical: String,
    val nudos: NumeroNudos
) : Tarea

enum class NumeroNudos(numeroNudos: String) {
    DOS("DOS"), CUATRO("CUATRO");

    companion object {
        fun from(numeroNudos: String): NumeroNudos {
            return when (numeroNudos.uppercase()) {
                "DOS" -> DOS
                "CUATRO" -> CUATRO
                else -> throw IllegalArgumentException("Número de nudos no válido.")
            }
        }
    }
}