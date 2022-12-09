package repositories

import db.HibernateManager
import models.TipoUsuario
import models.Turno
import models.Usuario
import org.junit.jupiter.api.*
import repositories.turno.TurnosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TurnosRepositoryTest {
    private val turnosRepository: TurnosRepository = TurnosRepository()
    private val usuariosRepository: UsuariosRepository = UsuariosRepository()

    private val usuario = Usuario(
        id = 6,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.ENCORDADOR
    )

    private val turno = Turno(
        id = 6,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 15, 20, 30),
        final = LocalDateTime.of(2022, 12, 31, 9, 15),
        encordador = usuario
    )

    @AfterEach
    fun tearDown() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM TURNOS")
            query.executeUpdate()
        }
    }

    @BeforeEach
    fun beforeEach() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM TURNOS")
            query.executeUpdate()
        }
        HibernateManager.open()
        HibernateManager.close()
    }

    @Test
    fun findAll() {
        val res = turnosRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() {
        turnosRepository.save(turno)

        val res = turnosRepository.findById(turno.id)

        assert(res == turno)
    }

    @Test
    fun findByIdNoExiste() {
        val res = turnosRepository.findById(-5)

        assert(res == null)

    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(usuario)
        val res = turnosRepository.save(turno)

        assertAll(
            { Assertions.assertEquals(res.id, turno.id) },
            { Assertions.assertEquals(res.uuid, turno.uuid) },
            { Assertions.assertEquals(res.comienzo, turno.comienzo) },
            { Assertions.assertEquals(res.final, turno.final) },
            { Assertions.assertEquals(res.encordador, turno.encordador) }
        )
    }

    @Test
    fun delete() {
        usuariosRepository.save(usuario)
        turnosRepository.save(turno)

        val res = turnosRepository.delete(turno)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = turnosRepository.delete(turno)

        assert(!res)
    }
}