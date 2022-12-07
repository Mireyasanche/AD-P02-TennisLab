package repositories.usuario

import config.AppConfig
import db.DataBaseManager
import entities.UsuariosDAO
import exceptions.UsuarioException
import models.TipoUsuario
import models.Usuario
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UsuariosRepositoryTest {

    private val usuariosRepository = UsuariosRepository(UsuariosDAO)

    private val usuario = Usuario(
        id = -1,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.TENISTA
    )

    @BeforeAll
    fun setUp() {
        DataBaseManager.init(AppConfig.DEFAULT)
    }

    @AfterAll
    fun tearDown() {
        DataBaseManager.dropTables()
    }

    @BeforeEach
    fun beforeEach() {
        DataBaseManager.clearTables()
    }

    @Test
    fun findAll() {
        val res = usuariosRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() = transaction {
        UsuariosDAO.new(usuario.id) {
            uuid = usuario.uuid
            nombre = usuario.nombre
            apellido = usuario.apellido
            email = usuario.email
            contrasena = usuario.contrasena
            perfil = usuario.perfil

        }

        val res = usuariosRepository.findById(usuario.id)

        assert(res == usuario)
    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<UsuarioException> {
            val res = usuariosRepository.findById(-5)
        }

    }

    @Test
    fun saveInsert() {
        val res = usuariosRepository.save(usuario)

        assertAll(
            { assertEquals(res.id, usuario.id) },
            { assertEquals(res.uuid, usuario.uuid) },
            { assertEquals(res.nombre, usuario.nombre) },
            { assertEquals(res.apellido, usuario.apellido) },
            { assertEquals(res.email, usuario.email) },
            { assertEquals(res.perfil,usuario.perfil) },
        )
    }

    @Test
    fun saveUpdate() = transaction {
        UsuariosDAO.new(usuario.id) {
            uuid = usuario.uuid
            nombre = usuario.nombre
            apellido = usuario.apellido
            email = usuario.email
            contrasena = usuario.contrasena
            perfil = usuario.perfil
        }

        val res = usuariosRepository.save(usuario)

        assert(res == usuario)
    }

    @Test
    fun delete() = transaction {
        UsuariosDAO.new(usuario.id) {
            uuid = usuario.uuid
            nombre = usuario.nombre
            apellido = usuario.apellido
            email = usuario.email
            contrasena = usuario.contrasena
            perfil = usuario.perfil
        }

        val res = usuariosRepository.delete(usuario)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = usuariosRepository.delete(usuario)

        assert(!res)
    }
}