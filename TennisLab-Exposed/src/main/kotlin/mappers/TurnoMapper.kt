/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package mappers

import entities.TurnosDAO
import models.Turno


/**
 * Esta función de extensión de TurnosDAO se ocupa de convertir el objeto de acceso de datos (DAO)
 * a su tipo original, como estructura en su modelo.
 *
 * @return Turno, el objeto convertido a modelo.
 */
fun TurnosDAO.fromTurnoDAOToTurno(): Turno {
    return Turno(
        id = id.value,
        uuid = uuid,
        comienzo = comienzo,
        final = final,
        encordador = encordador.fromUsuarioDAOToUsuario()
    )
}