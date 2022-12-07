package models.maquinas

import models.Turno
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "MAQUINA_PERSONALIZAR")
@NamedQuery(name = "MaquinaPersonalizar.findAll", query = "SELECT m FROM MaquinaPersonalizar m")
class MaquinaPersonalizar(
    @Id @GeneratedValue
    override val id : Int,
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    override val uuid: UUID,
    override val marca: String,
    override val modelo: String,
    @Column(name = "fecha_adquision")
    @Type(type = "org.hibernate.type.LocalDateType")
    @CreationTimestamp
    override val fechaAdquisicion: LocalDate,
    override val numeroSerie: Int,
    @OneToOne
    @JoinColumn(name = "turno_id", referencedColumnName = "id", nullable = true)
    override val turno: Turno,
    val mideManiobrabilidad: Boolean,
    val balance: Float,
    val rigidez: Float

) : Maquina