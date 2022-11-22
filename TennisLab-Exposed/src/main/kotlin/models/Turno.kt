package models

import java.time.LocalDateTime

data class Turno (
    val comienzo : LocalDateTime,
    val final : LocalDateTime,
    val maquina: Maquina,
    val encordador: Usuario
    )