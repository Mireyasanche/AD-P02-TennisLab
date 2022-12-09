/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.usuario

import models.Usuario
import repositories.ICRUDRepository

interface IUsuariosRepository : ICRUDRepository<Usuario, Int>