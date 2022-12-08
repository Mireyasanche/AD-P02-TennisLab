package models

import org.hibernate.annotations.NamedQuery
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TURNOS")
@NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t")
data class Turno(
    @Id
    val id: Int,

    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID,

    @Type(type = "org.hibernate.type.LocalDateTimeType")
    val comienzo: LocalDateTime,

    @Type(type = "org.hibernate.type.LocalDateTimeType")
    val final: LocalDateTime,

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    val encordador: Usuario
)