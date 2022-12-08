/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package models

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*
@Entity
@Table(name = "PRODUCTO")
@NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
data class Producto(
    @Id
    val id : Int,
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID,
    val tipoProducto: TipoProducto,
    val marca: String,
    val modelo: String,
    val precio: Float,
    val stock: Int,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = true)
    val pedido: Pedido
)

enum class TipoProducto(val tipoProducto: String) {
    RAQUETA("RAQUETA"), CORDAJE("CORDAJE"), COMPLEMENTO("COMPLEMENTO");

    /**
     * Función que parsea los Enums del objeto Producto. Indicando su equivalencia al valor de tipo String.
     *
     * @param producto El enum en formato String equivalente al valor de la clase Enum.
     *
     * @throws IllegalArgumentException Excepción que el método lanzará si el String que trata de parsear no eixste, o el tipo no existe.
     *
     * @return TipoProducto, un enum que variará dependiendo del valor de dicha variable.
     */
    companion object {
        fun from(producto: String): TipoProducto {
            return when (producto.uppercase()) {
                "RAQUETA" -> RAQUETA
                "CORDAJE" -> CORDAJE
                "COMPLEMENTO" -> COMPLEMENTO
                else -> throw IllegalArgumentException("Producto no válido.")
            }
        }
    }
}