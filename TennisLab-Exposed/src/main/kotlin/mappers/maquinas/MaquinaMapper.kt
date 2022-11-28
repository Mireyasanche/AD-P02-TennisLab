package mappers.maquinas

import entities.maquinas.MaquinasDAO
import entities.maquinas.MaquinasEncordarTable.tensionMaxima
import entities.maquinas.MaquinasEncordarTable.tensionMinima
import entities.maquinas.MaquinasEncordarTable.tipo
import entities.maquinas.MaquinasPersonalizarTable.balance
import entities.maquinas.MaquinasPersonalizarTable.mideManiobrabilidad
import entities.maquinas.MaquinasPersonalizarTable.rigidez
import models.maquinas.MaquinaEncordar
import models.maquinas.MaquinaPersonalizar
import models.maquinas.TipoEncordaje

fun MaquinasDAO.fromMaquinaEncordarDAOToMaquinaEncordar(): MaquinaEncordar {
    return MaquinaEncordar(
        id = 0,
        uuid = id.value,
        marca = marca,
        modelo = modelo,
        fechaAdquisicion = fechaAdquisicion,
        numeroSerie = numeroSerie,
        tipo = TipoEncordaje.from(tipo.toString()),
        tensionMaxima = tensionMaxima.toString().toFloat(),
        tensionMinima = tensionMinima.toString().toFloat(),
    )
}

fun MaquinasDAO.fromMaquinaPersonalizarDAOToMaquinaPersonalizar(): MaquinaPersonalizar {
    return MaquinaPersonalizar(
        id = 0,
        uuid = id.value,
        marca = marca,
        modelo = modelo,
        fechaAdquisicion = fechaAdquisicion,
        numeroSerie = numeroSerie,
        mideManiobrabilidad = mideManiobrabilidad.toString().toBoolean(),
        balance = balance.toString().toFloat(),
        rigidez = rigidez.toString().toFloat()
    )
}