package models.tareas

import models.Pedido
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TAREAS_PERSONALIZACION")
@NamedQuery(name = "TareaPersonalizacion.findAll", query = "SELECT t FROM TareaPersonalizacion t")
data class TareaPersonalizacion(
    @Id
    override val id: Int,

    @Column(name = "uuid")
    @Type(type = "uuid-char")
    override val uuid: UUID,

    override val precio: Float,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    override val pedido: Pedido,
    val peso: Float,
    val balance: Float,
    val rigidez: Float
) : Tarea
