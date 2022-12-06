package mappers.maquinas

import entities.maquinas.MaquinasEncordarDAO
import entities.maquinas.MaquinasEncordarTable.tensionMaxima
import entities.maquinas.MaquinasEncordarTable.tensionMinima
import entities.maquinas.MaquinasEncordarTable.tipo
import entities.maquinas.MaquinasPersonalizarDAO
import entities.maquinas.MaquinasPersonalizarTable.balance
import entities.maquinas.MaquinasPersonalizarTable.mideManiobrabilidad
import entities.maquinas.MaquinasPersonalizarTable.rigidez
import mappers.fromTurnoDAOToTurno
import models.maquinas.MaquinaEncordar
import models.maquinas.MaquinaPersonalizar
import models.maquinas.TipoEncordaje

fun MaquinasEncordarDAO.fromMaquinaEncordarDAOToMaquinaEncordar(): MaquinaEncordar {
    return MaquinaEncordar(
        id = id.value,
        uuid = uuid,
        marca = marca,
        modelo = modelo,
        fechaAdquisicion = fechaAdquisicion,
        numeroSerie = numeroSerie,
        turno = turno.fromTurnoDAOToTurno(),
        tipo = TipoEncordaje.from(tipo),
        tensionMaxima = tensionMaxima,
        tensionMinima = tensionMinima,
    )
}

fun MaquinasPersonalizarDAO.fromMaquinaPersonalizarDAOToMaquinaPersonalizar(): MaquinaPersonalizar {
    return MaquinaPersonalizar(
        id = id.value,
        uuid = uuid,
        marca = marca,
        modelo = modelo,
        fechaAdquisicion = fechaAdquisicion,
        numeroSerie = numeroSerie,
        turno = turno.fromTurnoDAOToTurno(),
        mideManiobrabilidad = mideManiobrabilidad,
        balance = balance,
        rigidez = rigidez
    )
}