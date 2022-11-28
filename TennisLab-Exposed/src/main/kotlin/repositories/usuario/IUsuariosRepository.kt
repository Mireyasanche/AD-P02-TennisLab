package repositories.usuario

import models.Usuario
import repositories.ICRUDRepository
import java.util.*

interface IUsuariosRepository : ICRUDRepository<Usuario, UUID>