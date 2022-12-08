package repositories.maquinas

import db.HibernateManager
import models.TipoUsuario
import models.Turno
import models.Usuario
import models.maquinas.MaquinaEncordar
import models.maquinas.TipoEncordaje
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertAll
import repositories.maquinas.MaquinasEncordarRepository
import repositories.turno.TurnosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MaquinasEncordarRepositoryTest {
    private val maquinasEncordarRepository: MaquinasEncordarRepository = MaquinasEncordarRepository()
    private val turnosRepository: TurnosRepository = TurnosRepository()
    private val usuariosRepository: UsuariosRepository = UsuariosRepository()

    private val usuario = Usuario(
        id = 7,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test",
        contrasena = "Test",
        perfil = TipoUsuario.ENCORDADOR
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

    @AfterAll
    fun tearDown() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM MAQUINAS_ENCORDAR")
            query.executeUpdate()
        }
    }

    @BeforeEach
    fun beforeEach() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM MAQUINAS_ENCORDAR")
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
        val res = maquinasEncordarRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() {
        maquinasEncordarRepository.save(maquina)

        val res = maquinasEncordarRepository.findById(maquina.id)

        assert(res == maquina)
    }

    @Test
    fun findByIdNoExiste() {
        val res = maquinasEncordarRepository.findById(-5)

        assert(res == null)

    }

    @Test
    fun saveInsert() {
        val res = maquinasEncordarRepository.save(maquina)

        assertAll(
            { Assertions.assertEquals(res.id, maquina.id) },
            { Assertions.assertEquals(res.uuid, maquina.uuid) },
            { Assertions.assertEquals(res.marca, maquina.marca) },
            { Assertions.assertEquals(res.modelo, maquina.modelo) },
            { Assertions.assertEquals(res.fechaAdquisicion, maquina.fechaAdquisicion) },
            { Assertions.assertEquals(res.numeroSerie, maquina.numeroSerie) },
            { Assertions.assertEquals(res.turno, maquina.turno) },
            { Assertions.assertEquals(res.tipo, maquina.tipo) },
            { Assertions.assertEquals(res.tensionMaxima, maquina.tensionMaxima) },
            { Assertions.assertEquals(res.tensionMinima, maquina.tensionMinima) }
        )
    }

    @Test
    fun delete() {
        maquinasEncordarRepository.save(maquina)

        val res = maquinasEncordarRepository.delete(maquina)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = maquinasEncordarRepository.delete(maquina)

        assert(!res)
    }
}