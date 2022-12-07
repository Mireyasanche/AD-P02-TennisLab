package controllers

import exceptions.UsuarioException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.TipoUsuario
import models.Usuario
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuario.UsuariosRepository
import java.util.*

@ExtendWith(MockKExtension::class)
internal class UsuariosControllerTest {

    @MockK
    lateinit var usuariosRepository: UsuariosRepository

    @InjectMockKs
    lateinit var usuariosController: UsuariosController

    private val usuario = Usuario(
        id = -1,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.TENISTA
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getUsuarios() {
        every { usuariosRepository.findAll() } returns listOf(usuario)

        val res = usuariosController.getUsuarios()

        assert(res == listOf(usuario))

        verify(exactly = 1) { usuariosRepository.findAll() }
    }

    @Test
    fun getUsuarioById() {
        every { usuariosRepository.findById(usuario.id) } returns usuario

        val res = usuariosController.getUsuarioById(usuario.id)

        assert(res == usuario)

        verify(exactly = 1) { usuariosRepository.findById(usuario.id) }

    }

    @Test
    fun getByIdNoExiste() {
        every { usuariosRepository.findById(usuario.id) } throws UsuarioException("Usuario no encontrado con id: ${usuario.id}")

        val res = assertThrows<UsuarioException> {
            usuariosController.getUsuarioById(usuario.id)
        }

        assert(res.message == "Usuario no encontrado con id: ${usuario.id}")

        verify(exactly = 1) { usuariosRepository.findById(usuario.id) }
    }

    @Test
    fun saveUsuario() {
        every { usuariosRepository.save(usuario) } returns usuario

        val res = usuariosController.saveUsuario(usuario)

        assert(res == usuario)

        verify(exactly = 1) { usuariosRepository.save(usuario) }
    }

    @Test
    fun deleteUsuario() {
        every { usuariosRepository.delete(usuario) } returns true

        val res = usuariosController.deleteUsuario(usuario)

        assert(res)

        verify(exactly = 1) { usuariosRepository.delete(usuario) }
    }

    @Test
    fun deleteNoExiste() {
        every { usuariosRepository.delete(usuario) } throws UsuarioException("Usuario no encontrado con id: ${usuario.id}")

        val res = assertThrows<UsuarioException> { usuariosController.deleteUsuario(usuario) }

        assert(res.message == "Usuario no encontrado con id: ${usuario.id}")

        verify(exactly = 1) { usuariosRepository.delete(usuario) }
    }

}