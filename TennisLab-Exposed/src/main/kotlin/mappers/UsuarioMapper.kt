package mappers

import entities.UsuariosDAO
import models.TipoUsuario
import models.Usuario

fun UsuariosDAO.fromUsuarioDAOToUsuario(): Usuario {
    return Usuario(
        id = 0,
        uuid = id.value,
        nombre = nombre,
        apellido = apellido,
        email = email,
        contrasena = contrasena,
        perfil = TipoUsuario.from(perfil),
    )
}