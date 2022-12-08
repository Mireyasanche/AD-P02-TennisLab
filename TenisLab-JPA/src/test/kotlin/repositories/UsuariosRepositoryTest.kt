package repositories

import db.HibernateManager
import models.TipoUsuario
import models.Usuario
import org.hibernate.boot.model.relational.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import repositories.usuario.UsuariosRepository
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UsuariosRepositoryTest {

    private val usuariosRepository = UsuariosRepository()

    private val usuario = Usuario(
        id = 2,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.ENCORDADOR
    )

    @AfterAll
    fun tearDown() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM USUARIO")
            query.executeUpdate()
        }
    }

    @BeforeEach
    fun beforeEach() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM USUARIO")
            query.executeUpdate()
        }
        HibernateManager.open()
        HibernateManager.close()
    }

    @Test
    fun findAll() {
        val res = usuariosRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() {
        usuariosRepository.save(usuario)

        val res = usuariosRepository.findById(usuario.id)

        assert(res == usuario)
    }

    @Test
    fun findByIdNoExiste() {
        val res = usuariosRepository.findById(-5)

        assert(res == null)

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
    fun delete() {
        usuariosRepository.save(usuario)

        val res = usuariosRepository.delete(usuario)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = usuariosRepository.delete(usuario)

        assert(!res)
    }
}