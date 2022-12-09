/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package models

import java.time.LocalDate
import java.util.*

data class Pedido(
    val id: Int,
    val uuid: UUID,
    val estado: TipoEstado,
    //un encordador puede tener maximo dos pedidos activos
    val encordador: Usuario,
    val fechaTope: LocalDate,
    val fechaEntrada: LocalDate,
    val fechaProgramada: LocalDate,
    val fechaEntrega: LocalDate,
    val precio: Float
)

enum class TipoEstado(estado: String) {
    RECIBIDO("RECIBIDO"),
    EN_PROCESO("EN_PROCESO"),
    TERMINADO("TERMINADO");
    /**
     * Función que parsea los Enums del objeto Pedido. Indicando su equivalencia al valor de tipo String.
     *
     * @param estado El enum en formato String equivalente al valor de la clase Enum.
     *
     * @throws IllegalArgumentException Excepción que el método lanzará si el String que trata de parsear no eixste, o el tipo no existe.
     *
     * @return TipoEstado, un enum que variará dependiendo del valor de dicha variable.
     */
    companion object {
        fun from(estado: String): TipoEstado {
            return when (estado.uppercase()) {
                "RECIBIDO" -> RECIBIDO
                "EN_PROCESO" -> EN_PROCESO
                "TERMINADO" -> TERMINADO
                else -> throw IllegalArgumentException("Estado no reconocido.")
            }
        }
    }
}