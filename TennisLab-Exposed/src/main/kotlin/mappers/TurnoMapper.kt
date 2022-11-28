package mappers

import entities.TurnosDAO
import mappers.maquinas.fromMaquinaEncordarDAOToMaquinaEncordar
import models.Turno

fun TurnosDAO.fromTurnoDAOToTurno(): Turno {
    return Turno(
        id = 0,
        uuid = id.value,
        comienzo = comienzo,
        final = final,
        //TODO: dependeiendo del turno se mapeará a una máquina de encordar o a una de personalizar
        maquina = maquina.fromMaquinaEncordarDAOToMaquinaEncordar(),
        encordador = encordador.fromUsuarioDAOToUsuario()

    )
}