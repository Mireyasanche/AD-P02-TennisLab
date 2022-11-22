package mappers

import entities.MaquinasEncordarDAO
import models.MaquinaEncordar
import models.TipoEncordaje

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