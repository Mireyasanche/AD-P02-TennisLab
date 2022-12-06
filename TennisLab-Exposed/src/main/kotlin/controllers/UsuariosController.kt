package controllers

import entities.UsuariosDAO
import models.Usuario
import repositories.usuario.UsuariosRepository

class UsuariosController(
    private val usuariosRepository: UsuariosRepository = UsuariosRepository(UsuariosDAO)
) {
    fun getUsuarios(): List<Usuario> {
        return usuariosRepository.findAll()
    }

    fun getUsuarioById(id: Int): Usuario {
        return usuariosRepository.findById(id)
    }

    fun saveUsuario(usuario: Usuario): Usuario {
        return usuariosRepository.save(usuario)
    }

    fun deleteUsuario(usuario: Usuario): Boolean {
        return usuariosRepository.delete(usuario)
    }
}