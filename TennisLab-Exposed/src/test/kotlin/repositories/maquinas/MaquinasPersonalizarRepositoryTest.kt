package repositories.maquinas

import config.AppConfig
import db.DataBaseManager
import entities.TurnosDAO
import entities.UsuariosDAO
import entities.maquinas.MaquinasEncordarDAO
import entities.maquinas.MaquinasPersonalizarDAO
import exceptions.maquinas.MaquinaEncordarException
import exceptions.maquinas.MaquinaPersonalizarException
import models.TipoUsuario
import models.Turno
import models.Usuario
import models.maquinas.MaquinaPersonalizar
import models.maquinas.TipoEncordaje
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import repositories.turno.TurnosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MaquinasPersonalizarRepositoryTest {
    private val maquinasPersonalizarRepository: MaquinasPersonalizarRepository = MaquinasPersonalizarRepository(
        MaquinasPersonalizarDAO, TurnosDAO
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

    private val maquina = MaquinaPersonalizar(
        id = 0,
        uuid = UUID.randomUUID(),
        marca = "Test",
        modelo = "Test",
        fechaAdquisicion = LocalDate.of(2021, 11, 19),
        numeroSerie = 0,
        turno = turno,
        mideManiobrabilidad = false,
        balance = 00.0f,
        rigidez = 00.0f
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
        val res = maquinasPersonalizarRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() = transaction {
        MaquinasPersonalizarDAO.new(maquina.id) {
            uuid = maquina.uuid
            marca = maquina.marca
            modelo = maquina.modelo
            fechaAdquisicion = maquina.fechaAdquisicion
            numeroSerie = maquina.numeroSerie
            mideManiobrabilidad = maquina.mideManiobrabilidad
            balance = maquina.balance
            rigidez = maquina.rigidez
        }

        val res = maquinasPersonalizarRepository.findById(maquina.id)
        assert(res == maquina)
    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<MaquinaPersonalizarException> {
            maquinasPersonalizarRepository.findById(maquina.id)
        }
    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(usuario)
        turnosRepository.save(turno)

        val res = maquinasPersonalizarRepository.save(maquina)

        Assertions.assertTrue(maquinasPersonalizarRepository.findAll().size == 1)
    }
    @Test
    fun saveUpdate() = transaction {
        MaquinasPersonalizarDAO.new(maquina.id) {
            uuid = maquina.uuid
            marca = maquina.marca
            modelo = maquina.modelo
            fechaAdquisicion = maquina.fechaAdquisicion
            numeroSerie = maquina.numeroSerie
            mideManiobrabilidad = maquina.mideManiobrabilidad
            balance = maquina.balance
            rigidez = maquina.rigidez
        }

        val res = maquinasPersonalizarRepository.findById(maquina.id)
        assert(res == maquina)
    }

    @Test
    fun delete() = transaction {
        MaquinasPersonalizarDAO.new(maquina.id) {
            uuid = maquina.uuid
            marca = maquina.marca
            modelo = maquina.modelo
            fechaAdquisicion = maquina.fechaAdquisicion
            numeroSerie = maquina.numeroSerie
            mideManiobrabilidad = maquina.mideManiobrabilidad
            balance = maquina.balance
            rigidez = maquina.rigidez
        }

        val res = maquinasPersonalizarRepository.delete(maquina)
        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = maquinasPersonalizarRepository.delete(maquina)

        assert(!res)
    }
}