package repositories.turno

import config.AppConfig
import db.DataBaseManager
import db.getUsuariosInit
import entities.TurnosDAO
import entities.UsuariosDAO
import exceptions.TurnoException
import models.Turno
import org.junit.jupiter.api.*
import repositories.usuario.UsuariosRepository
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TurnosRepositoryTest {
    private val turnosRepository: TurnosRepository = TurnosRepository(TurnosDAO, UsuariosDAO)
    private val usuariosRepository: UsuariosRepository = UsuariosRepository(UsuariosDAO)

    private val turno = Turno(
        id = 0,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 15, 20, 30),
        final = LocalDateTime.of(2022, 12, 31, 9, 15),
        encordador = getUsuariosInit()[2]
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
        val res = turnosRepository.findAll()

        assert(res.isEmpty())
    }

//    @Test
//    fun findById() = transaction {
//        TurnosDAO.new(turno.id) {
//            uuid = turno.uuid
//            comienzo = turno.comienzo
//            final = turno.final
//            encordador = turno.encordador
//        }
//
//        val res = turnosRepository.findById(turno.id)
//        assert(res == turno)
//    }

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

//    @Test
//    fun saveUpdate() = transaction {
//        TurnosDAO.new(turno.id) {
//            uuid = turno.uuid
//            comienzo = turno.comienzo
//            final = turno.final
//            encordador = turno.encordador
//        }
//
//        val res = turnosRepository.save(turno)
//
//        assert(res == turno)
//    }

//    @Test
//    fun delete() = transaction {
//        TurnosDAO.new(turno.id) {
//            uuid = turno.uuid
//            comienzo = turno.comienzo
//            final = turno.final
//            encordador = turno.encordador
//        }
//
//        val res = turnosRepository.delete(turno)
//
//        assert(res)
//    }

    @Test
    fun deleteNoExiste() {
        val res = turnosRepository.delete(turno)

        assert(!res)
    }
}