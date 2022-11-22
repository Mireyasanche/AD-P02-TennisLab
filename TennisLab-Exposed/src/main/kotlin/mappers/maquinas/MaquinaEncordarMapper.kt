package mappers.maquinas

import entities.maquinas.MaquinasEncordarDAO
import models.maquinas.MaquinaEncordar
import models.maquinas.TipoEncordaje

fun MaquinasEncordarDAO.fromMaquinaEncordarDAOToMaquinaEncordar(): MaquinaEncordar {
    return MaquinaEncordar(
        id = 0,
        uuid = id.value,
        marca = marca.marca,
        modelo = modelo.modelo,
        fechaAdquisicion = fechaAdquisicion.fechaAdquisicion,
        numeroSerie = numeroSerie.numeroSerie,
        tipo = TipoEncordaje.from(tipo),
        tensionMaxima = tensionMaxima,
        tensionMinima = tensionMinima,
    )
}