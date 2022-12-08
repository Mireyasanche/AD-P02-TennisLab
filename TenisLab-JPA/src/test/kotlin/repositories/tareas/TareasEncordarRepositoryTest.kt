package repositories.tareas

import db.HibernateManager
import models.Pedido
import models.TipoEstado
import models.TipoUsuario
import models.Usuario
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado
import org.junit.jupiter.api.*
import repositories.pedido.PedidosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TareasEncordarRepositoryTest {
    private val tareasEncordadoRepository = TareasEncordadoRepository()
    private val usuariosRepository = UsuariosRepository()
    private val pedidosRepository = PedidosRepository()

    private val usuario = Usuario(
        id = 2,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.ENCORDADOR
    )

    private val pedido = Pedido(
        id = 2,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.EN_PROCESO,
        encordador = usuario,
        fechaTope = LocalDate.of(2000, 1, 1),
        fechaEntrada = LocalDate.of(2000, 1, 1),
        fechaProgramada = LocalDate.of(2000, 1, 1),
        fechaEntrega = LocalDate.of(2000, 1, 1),
        precio = 0.0f
    )

    private val tarea = TareaEncordado(
        id = 2,
        uuid = UUID.randomUUID(),
        precio = 0.0f,
        pedido = pedido,
        tensionHorizontal = 0.0f,
        cordajeHorizontal = "Test",
        tensionVertical = 0.0f,
        cordajeVertical = "Test",
        nudos = NumeroNudos.CUATRO
    )

    @AfterEach
    fun tearDown() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM TAREAS_ENCORDADO")
            query.executeUpdate()
        }
    }

    @BeforeEach
    fun beforeEach() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM TAREAS_ENCORDADO")
            query.executeUpdate()
        }

    }

    @BeforeAll
    fun setUp() {
        usuariosRepository.save(usuario)
        pedidosRepository.save(pedido)
    }

    @Test
    fun findAll() {
        val res = tareasEncordadoRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() {
        tareasEncordadoRepository.save(tarea)

        val res = tareasEncordadoRepository.findById(tarea.id)

        assert(res == tarea)
    }

    @Test
    fun findByIdNoExiste() {
        val res = tareasEncordadoRepository.findById(-5)

        assert(res == null)

    }

    @Test
    fun saveInsert() {
        val res = tareasEncordadoRepository.save(tarea)

        assertAll(
            { Assertions.assertEquals(res.id, tarea.id) },
            { Assertions.assertEquals(res.uuid, tarea.uuid) },
            { Assertions.assertEquals(res.precio, tarea.precio) },
            { Assertions.assertEquals(res.tensionHorizontal, tarea.tensionHorizontal) },
            { Assertions.assertEquals(res.tensionVertical, tarea.tensionVertical) },
            { Assertions.assertEquals(res.cordajeVertical, tarea.cordajeVertical) },
            { Assertions.assertEquals(res.cordajeHorizontal, tarea.cordajeHorizontal) },
            { Assertions.assertEquals(res.nudos, tarea.nudos) },
        )
    }

    @Test
    fun delete() {
        tareasEncordadoRepository.save(tarea)

        val res = tareasEncordadoRepository.delete(tarea)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = tareasEncordadoRepository.delete(tarea)

        assert(!res)
    }
}