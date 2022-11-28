package models

import models.maquinas.Maquina
import java.time.LocalDateTime
import java.util.*

data class Turno (
    val id : Int,
    val uuid: UUID,
    val comienzo : LocalDateTime,
    val final : LocalDateTime,
    val maquina: Maquina,
    val encordador: Usuario
    )