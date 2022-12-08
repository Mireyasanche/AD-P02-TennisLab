package repositories.tareas

import config.AppConfig
import db.DataBaseManager
import entities.PedidosDAO
import entities.tareas.TareasEncordadoDAO
import exceptions.tareas.TareaEncordadoException
import models.Pedido
import models.TipoEstado
import models.TipoUsuario
import models.Usuario
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TareasEncordadoRepositoryTest {
    private val tareasEncordadoRepository = TareasEncordadoRepository(TareasEncordadoDAO, PedidosDAO)

    private val usuario = Usuario(
        id = -1,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.TENISTA
    )

    private val pedido = Pedido(
        id = -1,
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
        id = -1,
        uuid = UUID.randomUUID(),
        precio = 0.0f,
        pedido = pedido,
        tensionHorizontal = 0.0f,
        cordajeHorizontal = "Test",
        tensionVertical = 0.0f,
        cordajeVertical = "Test",
        nudos = NumeroNudos.CUATRO
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
        val res = tareasEncordadoRepository.findAll()

        assert(res.isEmpty())
    }

//    @Test
//    fun findById()  = transaction {
//        TareasEncordadoDAO.new(tarea.id) {
//            uuid = tarea.uuid
//            precio = tarea.precio
//            pedido = tarea.pedido
//            tensionHorizontal = tarea.tensionHorizontal
//            tensionVertical = tarea.tensionVertical
//            cordajeVertical = tarea.cordajeVertical
//            cordajeHorizontal = tarea.cordajeHorizontal
//            nudos = tarea.nudos.toString()
//        }
//
//        val res = tareasEncordadoRepository.findById(tarea.id)
//
//        assert(res == tarea)
//    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<TareaEncordadoException> {
            val res = tareasEncordadoRepository.findById(-5)
        }

    }

    @Test
    fun saveInsert() {
        val res = tareasEncordadoRepository.save(tarea)

        assertAll(
            { assertEquals(res.id, tarea.id) },
            { assertEquals(res.uuid, tarea.uuid) },
            { assertEquals(res.precio, tarea.precio) },
            { assertEquals(res.tensionHorizontal, tarea.tensionHorizontal) },
            { assertEquals(res.tensionVertical, tarea.tensionVertical) },
            { assertEquals(res.cordajeVertical, tarea.cordajeVertical) },
            { assertEquals(res.cordajeHorizontal, tarea.cordajeHorizontal) },
            { assertEquals(res.nudos, tarea.nudos) },
        )
    }

//    @Test
//    fun saveUpdate() = transaction {
//        TareasEncordadoDAO.new(tarea.id) {
//            uuid = tarea.uuid
//            precio = tarea.precio
//            pedido = tarea.pedido
//            tensionHorizontal = tarea.tensionHorizontal
//            tensionVertical = tarea.tensionVertical
//            cordajeVertical = tarea.cordajeVertical
//            cordajeHorizontal = tarea.cordajeHorizontal
//            nudos = tarea.nudos.toString()
//        }
//
//        val res = tareasEncordadoRepository.save(tarea)
//
//        assert(res == tarea)
//    }

//    @Test
//    fun delete() = transaction {
//        TareasEncordadoDAO.new(tarea.id) {
//            uuid = tarea.uuid
//            precio = tarea.precio
//            pedido = tarea.pedido
//            tensionHorizontal = tarea.tensionHorizontal
//            tensionVertical = tarea.tensionVertical
//            cordajeVertical = tarea.cordajeVertical
//            cordajeHorizontal = tarea.cordajeHorizontal
//            nudos = tarea.nudos.toString()
//        }
//
//        val res = tareasEncordadoRepository.delete(tarea)
//
//        assert(res)
//    }

    @Test
    fun deleteNoExiste() {
        val res = tareasEncordadoRepository.delete(tarea)

        assert(!res)
    }
}