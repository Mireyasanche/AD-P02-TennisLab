package repositories.tareas

import config.AppConfig
import db.DataBaseManager
import db.getPedidosInit
import db.getUsuariosInit
import entities.PedidosDAO
import entities.TurnosDAO
import entities.UsuariosDAO
import entities.tareas.TareasEncordadoDAO
import models.Pedido
import models.TipoEstado
import models.TipoUsuario
import models.Usuario
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import repositories.pedido.PedidosRepository
import repositories.usuario.UsuariosRepository
import java.lang.IllegalStateException
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TareasEncordadoRepositoryTest {
    private val usuariosRepository = UsuariosRepository(UsuariosDAO)
    private val pedidosRepository = PedidosRepository(PedidosDAO, UsuariosDAO)
    private val tareasEncordadoRepository = TareasEncordadoRepository(TareasEncordadoDAO, PedidosDAO)

    private val usuario = Usuario(
        id = 2,
        uuid = UUID.randomUUID(),
        nombre = "María",
        apellido = "Sanz",
        email = "maria@sanz.com",
        contrasena = "1234",
        perfil = TipoUsuario.ENCORDADOR
    )

    private val pedido = Pedido(
        id = 2,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.RECIBIDO,
        encordador = usuario,
        fechaTope = LocalDate.of(2023, 1, 5),
        fechaEntrada = LocalDate.of(2022, 12, 7),
        fechaProgramada = LocalDate.of(2023, 1, 4),
        fechaEntrega = LocalDate.of(2022, 1, 4),
        precio = 57.0f
    )

    private val tarea = TareaEncordado(
        id = 0,
        uuid = UUID.randomUUID(),
        precio = 100.0f,
        pedido = pedido,
        tensionHorizontal = 22.5f,
        cordajeHorizontal = "Luxilon",
        tensionVertical = 22.5f,
        cordajeVertical = "Luxilon",
        nudos = NumeroNudos.CUATRO
    )

    @AfterEach
    fun tearDown(){
        DataBaseManager.dropTables()
    }

    @BeforeEach
    fun beforeEach() {
        DataBaseManager.init(AppConfig.DEFAULT)
        DataBaseManager.clearTables()
    }

    private fun saveData() = transaction {
        val usuarioDAO = UsuariosDAO.new(pedido.encordador.id){
            uuid = pedido.encordador.uuid
            nombre = pedido.encordador.nombre
            apellido = pedido.encordador.apellido
            email = pedido.encordador.email
            contrasena = pedido.encordador.contrasena
            perfil = pedido.encordador.perfil

        }

        val pedidoDAO = PedidosDAO.new(pedido.id) {
            uuid = pedido.uuid
            estado = pedido.estado.toString()
            encordador = usuarioDAO
            fechaTope = pedido.fechaTope
            fechaEntrada = pedido.fechaEntrada
            fechaProgramada = pedido.fechaProgramada
            fechaEntrega = pedido.fechaEntrega
            precio = pedido.precio
        }

        val tareaDAO = TareasEncordadoDAO.new(tarea.id) {
            uuid = tarea.uuid
            precio = tarea.precio
            pedido = pedidoDAO
            tensionHorizontal = tarea.tensionHorizontal
            tensionVertical = tarea.tensionVertical
            cordajeVertical = tarea.cordajeVertical
            cordajeHorizontal = tarea.cordajeHorizontal
            nudos = tarea.nudos.toString()
        }
    }

    @Test
    fun findAll() {
        val res = tareasEncordadoRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById()  = transaction {
        saveData()

        val res = tareasEncordadoRepository.findById(tarea.id)

        assert(res == tarea)
    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<IllegalStateException> {
            val res = tareasEncordadoRepository.findById(-5)
        }

    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(usuario)
        pedidosRepository.save(pedido)
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

    @Test
    fun saveUpdate() = transaction {
        saveData()

        val res = tareasEncordadoRepository.save(tarea)

        assert(res == tarea)
    }

    @Test
    fun delete() = transaction {
        saveData()

        val res = tareasEncordadoRepository.delete(tarea)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = tareasEncordadoRepository.delete(tarea)

        assert(!res)
    }
}