package models

import java.time.LocalDate
import java.util.*

data class Pedido(
    val id: Int,
    val uuid: UUID,
    val tareas: MutableList<Tarea>,
    val productos: MutableList<Producto>,
    val estado: TipoEstado,
    val encordador: Usuario,
    val fechaTope: LocalDate,
    val fechaEntrada: LocalDate,
    val fechaProgramada: LocalDate,
    val fechaEntrega: LocalDate,
    val precio: Float
)

enum class TipoEstado(estado: String) {
    RECIBIDO("RECIBIDO"), EN_PROCESO("EN PROCESO"), TERMINADO("TERMINADO");

    companion object {
        fun from (estado: String): TipoEstado {
            return when (estado.uppercase()) {
                "RECIBIDO" -> RECIBIDO
                "EN PROCESO" -> EN_PROCESO
                "TERMINADO" -> TERMINADO
                else -> throw IllegalArgumentException("Estado no reconocido.")
            }
        }
    }
}