package mappers

import entities.TurnosDAO
import models.Turno

fun TurnosDAO.fromTurnoDAOToTurno(): Turno {
    return Turno(
        comienzo = comienzo,
        final = final,
        maquina = maquina.fromMaquinaDAOToMaquina(),
        encordador = encordador.fromUsuarioDAOToUsuario()

    )
}