package mappers

import entities.UsuariosDAO
import models.Usuario

fun UsuariosDAO.fromUsuarioDAOToUsuario(): Usuario {
    return Usuario(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        contrasena = contrasena,
        perfil = perfil,
    )
}