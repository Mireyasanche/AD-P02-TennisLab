package db

import models.TipoUsuario
import models.Usuario
import java.util.*

fun getUsuariosInit() = listOf(
    Usuario(
        id = 0,
        uuid = UUID.randomUUID(),
        nombre = "pepe",
        apellido = "pepon",
        email= "pepe@pepon.com",
        contrasena ="pepe",
        perfil = TipoUsuario.TENISTA
    ),
    Usuario(
        id = 1,
        uuid = UUID.randomUUID(),
        nombre = "pepa",
        apellido = "pepona",
        email= "pepa@pepona.com",
        contrasena ="pepa",
        perfil = TipoUsuario.ADMINISTRADOR_ENCARGADO
    )
)