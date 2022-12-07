package models.tareas

import models.Pedido
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TAREAS_PERSONALIZACION")
@NamedQuery(name = "TareaPersonalizar.findAll", query = "SELECT t FROM TareaPersonalizacion t")
class TareaPersonalizacion(
    @Id @GeneratedValue
    override val id: Int,

    @Column(name = "uuid")
    @Type(type = "uuid-char")
    override val uuid: UUID,

    override val precio: Float,

    @OneToMany
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    override val pedido: Pedido,
    val peso: Float,
    val balance: Float,
    val rigidez: Float
) : Tarea
