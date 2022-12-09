/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
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

/**
 * Esta función de extensión de MaquinaEncrodarDAO se ocupa de convertir el objeto de acceso de datos (DAO)
 * a su tipo original, como estructura en su modelo.
 *
 * @return MaquinaEncordar, el objeto convertido a modelo.
 */
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

/**
 * Esta función de extensión de MaquinaPersonalizarDAO se ocupa de convertir el objeto de acceso de datos (DAO)
 * a su tipo original, como estructura en su modelo.
 *
 * @return MaquinaPersonalizar, el objeto convertido a modelo.
 */
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