package repositories

import config.AppConfig
import db.DataBaseManager
import db.getUsuariosInit
import entities.TurnosDAO
import entities.UsuariosDAO
import exceptions.TurnoException
import models.TipoUsuario
import models.Turno
import models.Usuario
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import repositories.turno.TurnosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TurnosRepositoryTest {
    private val turnosRepository: TurnosRepository = TurnosRepository(TurnosDAO, UsuariosDAO)
    private val usuariosRepository: UsuariosRepository = UsuariosRepository(UsuariosDAO)


    private val usuario = Usuario(
        id = 2,
        uuid = UUID.randomUUID(),
        nombre = "María",
        apellido = "Sanz",
        email = "maria@sanz.com",
        contrasena = "1234",
        perfil = TipoUsuario.ENCORDADOR
    )

    private val turno = Turno(
        id = 0,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 15, 20, 30),
        final = LocalDateTime.of(2022, 12, 31, 9, 15),
        encordador = usuario
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

    private fun saveData() = transaction {
        val usuarioDAO = UsuariosDAO.new(turno.encordador.id) {
            uuid = turno.encordador.uuid
            nombre = turno.encordador.nombre
            apellido = turno.encordador.apellido
            email = turno.encordador.email
            contrasena = turno.encordador.contrasena
            perfil = turno.encordador.perfil

        }

        TurnosDAO.new(turno.id) {
            uuid = turno.uuid
            comienzo = turno.comienzo
            final = turno.final
            encordador = usuarioDAO
        }
    }

    @Test
    fun findAll() {
        val res = turnosRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() = transaction {
        saveData()

        val res = turnosRepository.findById(turno.id)
        assert(res == turno)
    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<TurnoException> {
            turnosRepository.findById(turno.id)
        }
    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(getUsuariosInit()[2])
        val res = turnosRepository.save(turno)

        assert(turnosRepository.findAll()[0] == res)
    }

    @Test
    fun saveUpdate() = transaction {
        saveData()

        val res = turnosRepository.save(turno)

        assert(res == turno)
    }

    @Test
    fun delete() = transaction {
        saveData()

        val res = turnosRepository.delete(turno)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = turnosRepository.delete(turno)

        assert(!res)
    }
}