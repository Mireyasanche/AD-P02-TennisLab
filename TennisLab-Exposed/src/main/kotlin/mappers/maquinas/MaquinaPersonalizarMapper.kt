package mappers.maquinas

import entities.maquinas.MaquinasPersonalizarDAO
import models.maquinas.MaquinaPersonalizar

fun MaquinasPersonalizarDAO.fromMaquinaPersonalizarDAOToMaquinaPersonalizar(): MaquinaPersonalizar {
    return MaquinaPersonalizar(
        id = 0,
        uuid = id.value,
        marca = marca.marca,
        modelo = modelo.modelo,
        fechaAdquisicion = fechaAdquisicion.fechaAdquisicion,
        numeroSerie = numeroSerie.numeroSerie,
        mideManiobrabilidad = mideManiobrabilidad,
        balance = balance,
        rigidez = rigidez
    )
}
