package repositories.usuario

import models.Usuario
import repositories.ICRUDRepository

interface IUsuariosRepository : ICRUDRepository<Usuario, Int>