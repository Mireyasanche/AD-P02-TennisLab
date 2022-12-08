package repositories.maquinas

import config.AppConfig
import db.DataBaseManager
import entities.TurnosDAO
import entities.UsuariosDAO
import entities.maquinas.MaquinasEncordarDAO
import exceptions.maquinas.MaquinaEncordarException
import models.TipoUsuario
import models.Turno
import models.Usuario
import models.maquinas.MaquinaEncordar
import models.maquinas.TipoEncordaje
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import repositories.turno.TurnosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MaquinasEncordarRepositoryTest {
    private val maquinasEncordarRepository: MaquinasEncordarRepository = MaquinasEncordarRepository(
        MaquinasEncordarDAO, TurnosDAO
    )
    private val turnosRepository: TurnosRepository = TurnosRepository(TurnosDAO, UsuariosDAO)
    private val usuariosRepository: UsuariosRepository = UsuariosRepository(UsuariosDAO)

    private val usuario = Usuario(
        id = 0,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test",
        contrasena = "Test",
        perfil = TipoUsuario.TENISTA
    )

    private val turno = Turno(
        id = 0,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 15, 20, 30),
        final = LocalDateTime.of(2022, 12, 31, 9, 15),
        encordador = usuario
    )

    private val maquina = MaquinaEncordar(
        id = 0,
        uuid = UUID.randomUUID(),
        marca = "Test",
        modelo = "Test",
        fechaAdquisicion = LocalDate.of(2021, 11, 19),
        numeroSerie = 0,
        turno = turno,
        tipo = TipoEncordaje.AUTOMATICA,
        tensionMaxima = 00.0f,
        tensionMinima = 00.0f
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
        val res = maquinasEncordarRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() = transaction {
        MaquinasEncordarDAO.new(maquina.id) {
            uuid = maquina.uuid
            marca = maquina.marca
            modelo = maquina.modelo
            fechaAdquisicion = maquina.fechaAdquisicion
            numeroSerie = maquina.numeroSerie
            tipo = maquina.tipo.toString()
            tensionMaxima = maquina.tensionMaxima
            tensionMinima = maquina.tensionMinima
        }

       val res = maquinasEncordarRepository.findById(maquina.id)
        assert(res == maquina)
    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<MaquinaEncordarException> {
            maquinasEncordarRepository.findById(maquina.id)
        }
    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(usuario)
        turnosRepository.save(turno)

        val res = maquinasEncordarRepository.save(maquina)

        assertTrue(maquinasEncordarRepository.findAll().size == 1)
    }
    @Test
    fun saveUpdate() = transaction {
        MaquinasEncordarDAO.new(maquina.id) {
            uuid = maquina.uuid
            marca = maquina.marca
            modelo = maquina.modelo
            fechaAdquisicion = maquina.fechaAdquisicion
            numeroSerie = maquina.numeroSerie
            tipo = maquina.tipo.toString()
            tensionMaxima = maquina.tensionMaxima
            tensionMinima = maquina.tensionMinima
        }

        val res = maquinasEncordarRepository.findById(maquina.id)
        assert(res == maquina)
    }

    @Test
    fun delete() = transaction {
        MaquinasEncordarDAO.new(maquina.id) {
           uuid = maquina.uuid
            marca = maquina.marca
            modelo = maquina.modelo
            fechaAdquisicion = maquina.fechaAdquisicion
            numeroSerie = maquina.numeroSerie
            tipo = maquina.tipo.toString()
            tensionMaxima = maquina.tensionMaxima
            tensionMinima = maquina.tensionMinima
        }

        val res = maquinasEncordarRepository.delete(maquina)
        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = maquinasEncordarRepository.delete(maquina)

        assert(!res)
    }
}