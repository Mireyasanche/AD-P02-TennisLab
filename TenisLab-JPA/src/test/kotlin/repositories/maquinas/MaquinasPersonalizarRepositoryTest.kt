package repositories.maquinas

import db.HibernateManager
import models.TipoUsuario
import models.Turno
import models.Usuario
import models.maquinas.MaquinaPersonalizar
import org.junit.jupiter.api.*
import repositories.turno.TurnosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MaquinasPersonalizarRepositoryTest {
    private val maquinasPersonalizarRepository: MaquinasPersonalizarRepository = MaquinasPersonalizarRepository()
    private val turnosRepository: TurnosRepository = TurnosRepository()
    private val usuariosRepository: UsuariosRepository = UsuariosRepository()

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

    @AfterAll
    fun tearDown() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM MAQUINA_PERSONALIZAR")
            query.executeUpdate()
        }
    }

    @BeforeEach
    fun beforeEach() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM MAQUINA_PERSONALIZAR")
            query.executeUpdate()
        }
        HibernateManager.open()
        HibernateManager.close()
    }

    @BeforeAll
    fun setUp() {
        usuariosRepository.save(usuario)
        turnosRepository.save(turno)
    }

    @Test
    fun findAll() {
        val res = maquinasPersonalizarRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() {
        maquinasPersonalizarRepository.save(maquina)

        val res = maquinasPersonalizarRepository.findById(maquina.id)
        println(res)
        println(maquina)

        assert(res == maquina)
    }

    @Test
    fun findByIdNoExiste() {
        val res = maquinasPersonalizarRepository.findById(-5)

        assert(res == null)

    }

    @Test
    fun saveInsert() {
        val res = maquinasPersonalizarRepository.save(maquina)

        Assertions.assertAll(
            { Assertions.assertEquals(res.id, maquina.id) },
            { Assertions.assertEquals(res.uuid, maquina.uuid) },
            { Assertions.assertEquals(res.marca, maquina.marca) },
            { Assertions.assertEquals(res.modelo, maquina.modelo) },
            { Assertions.assertEquals(res.fechaAdquisicion, maquina.fechaAdquisicion) },
            { Assertions.assertEquals(res.numeroSerie, maquina.numeroSerie) },
            { Assertions.assertEquals(res.turno, maquina.turno) },
            { Assertions.assertEquals(res.mideManiobrabilidad, maquina.mideManiobrabilidad) },
            { Assertions.assertEquals(res.balance, maquina.balance) },
            { Assertions.assertEquals(res.rigidez, maquina.rigidez) }
        )
    }

    @Test
    fun delete() {
        maquinasPersonalizarRepository.save(maquina)

        val res = maquinasPersonalizarRepository.delete(maquina)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = maquinasPersonalizarRepository.delete(maquina)

        assert(!res)
    }
}