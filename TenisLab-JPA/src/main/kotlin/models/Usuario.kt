/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package models

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "USUARIO")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
data class Usuario(
    @Id
    val id : Int,
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID,
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasena: String,
    val perfil: TipoUsuario
    )

enum class TipoUsuario(valor: String){
    ADMINISTRADOR_ENCARGADO("ADMINISTRADOR_ENCARGADO"),
    ADMINISTRADOR_JEFE("ADMINISTRADOR_JEFE"),
    ENCORDADOR("ENCORDADOR"),
    TENISTA("TENISTA");
    /**
     * Función que parsea los Enums del objeto Usuario. Indicando su equivalencia al valor de tipo String.
     *
     * @param tipoUsuario El enum en formato String equivalente al valor de la clase Enum.
     *
     * @throws IllegalArgumentException Excepción que el método lanzará si el String que trata de parsear no eixste, o el tipo no existe.
     *
     * @return TipoUsuario, un enum que variará dependiendo del valor de dicha variable.
     */
    companion object {
        fun from(tipoUsuario: String): TipoUsuario {
            return when (tipoUsuario.uppercase()) {
                "ADMINISTRADOR_ENCARGADO" -> ADMINISTRADOR_ENCARGADO
                "ADMINISTRADOR_JEFE" -> ADMINISTRADOR_JEFE
                "ENCORDADOR" -> ENCORDADOR
                "TENISTA" -> TENISTA
                else -> throw IllegalArgumentException("TipoUsuario no reconocido")
            }
        }
    }
}
