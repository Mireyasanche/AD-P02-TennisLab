package mappers

import entities.TurnosDAO
import models.Turno

fun TurnosDAO.fromTurnoDAOToTurno(): Turno {
    return Turno(
        id = id.value,
        uuid = uuid,
        comienzo = comienzo,
        final = final,
        encordador = encordador.fromUsuarioDAOToUsuario()
    )
}