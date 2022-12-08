package repositories.tareas

import config.AppConfig
import db.DataBaseManager
import db.getPedidosInit
import entities.PedidosDAO
import entities.tareas.TareasPersonalizacionDAO
import exceptions.tareas.TareaPersonalizacionException
import models.Pedido
import models.TipoEstado
import models.TipoUsuario
import models.Usuario
import models.tareas.TareaPersonalizacion
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TareasPersonalizacionRepositoryTest {
    private val tareasPersonalizacionRepository = TareasPersonalizacionRepository(TareasPersonalizacionDAO, PedidosDAO)


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

    private val tarea = TareaPersonalizacion(
        id = 0,
        uuid = UUID.randomUUID(),
        precio = 70.0f,
        pedido = getPedidosInit()[1],
        peso = 0.3f,
        balance = 320.0f,
        rigidez = 70.0f
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
        val res = tareasPersonalizacionRepository.findAll()

        assert(res.isEmpty())
    }

//    @Test
//    fun findById() = transaction {
//        TareasPersonalizacionDAO.new(tarea.id) {
//            uuid = tarea.uuid
//            precio = tarea.precio
//            pedido = tarea.pedido
//            peso = tarea.peso
//            balance = tarea.balance
//            rigidez = tarea.rigidez
//        }
//
//        val res = tareasPersonalizacionRepository.findById(tarea.id)
//
//        assert(res == tarea)
//    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<TareaPersonalizacionException> {
            val res = tareasPersonalizacionRepository.findById(-5)
        }
    }

    @Test
    fun saveInsert() {
        val res = tareasPersonalizacionRepository.save(tarea)

        assertAll(
            { assertEquals(res.id, tarea.id) },
            { assertEquals(res.uuid, tarea.uuid) },
            { assertEquals(res.precio, tarea.precio) },
            { assertEquals(res.peso, tarea.peso) },
            { assertEquals(res.balance, tarea.balance) },
            { assertEquals(res.rigidez, tarea.rigidez) },
        )
    }

//    @Test
//    fun saveUpdate() = transaction {
//        TareasPersonalizacionDAO.new(tarea.id) {
//            uuid = tarea.uuid
//            precio = tarea.precio
//            pedido = tarea.pedido
//            peso = tarea.peso
//            balance = tarea.balance
//            rigidez = tarea.rigidez
//        }
//
//        val res = tareasPersonalizacionRepository.save(tarea)
//
//        assert(res == tarea)
//    }

//    @Test
//    fun delete() = transaction {
//        TareasPersonalizacionDAO.new(tarea.id) {
//            uuid = tarea.uuid
//            precio = tarea.precio
//            pedido = tarea.pedido
//            peso = tarea.peso
//            balance = tarea.balance
//            rigidez = tarea.rigidez
//        }
//
//        val res = tareasPersonalizacionRepository.delete(tarea)
//
//        assert(res)
//    }

    @Test
    fun deleteNoExiste() {
        val res = tareasPersonalizacionRepository.delete(tarea)

        assert(!res)
    }
}