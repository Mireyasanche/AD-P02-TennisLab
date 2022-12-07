package models.tareas

import models.Pedido
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TAREAS_ENCORDADO")
class TareaEncordado(
    @Id @GeneratedValue
    override val id: Int,

    @Column(name = "uuid")
    @Type(type = "uuid-char")
    override val uuid: UUID,

    override val precio: Float,

    @OneToMany
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