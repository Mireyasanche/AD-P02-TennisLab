/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package models.tareas

import models.Pedido
import java.util.*

data class TareaEncordado(
    override val id: Int,
    override val uuid: UUID,
    override val precio: Float,
    override val pedido: Pedido,
    val tensionHorizontal: Float,
    val cordajeHorizontal: String,
    val tensionVertical: Float,
    val cordajeVertical: String,
    val nudos: NumeroNudos
) : Tarea

enum class NumeroNudos(numeroNudos: String) {
    DOS("DOS"), CUATRO("CUATRO");
    /**
     * Función que parsea los Enums del objeto TareaEncordado. Indicando su equivalencia al valor de tipo String.
     *
     * @param numeroNudos El enum en formato String equivalente al valor de la clase Enum.
     *
     * @throws IllegalArgumentException Excepción que el método lanzará si el String que trata de parsear no eixste, o el tipo no existe.
     *
     * @return NumeroNudos, un enum que variará dependiendo del valor de dicha variable.
     */
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