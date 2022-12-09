package repositories.tareas

import config.AppConfig
import db.DataBaseManager
import db.getPedidosInit
import entities.PedidosDAO
import entities.UsuariosDAO
import entities.tareas.TareasEncordadoDAO
import entities.tareas.TareasPersonalizacionDAO
import exceptions.tareas.TareaPersonalizacionException
import models.Pedido
import models.TipoEstado
import models.TipoUsuario
import models.Usuario
import models.tareas.TareaPersonalizacion
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import repositories.pedido.PedidosRepository
import repositories.usuario.UsuariosRepository
import java.lang.IllegalStateException
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TareasPersonalizacionRepositoryTest {
    private val usuariosRepository = UsuariosRepository(UsuariosDAO)
    private val pedidosRepository = PedidosRepository(PedidosDAO, UsuariosDAO)
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
        pedido = pedido,
        peso = 0.3f,
        balance = 320.0f,
        rigidez = 70.0f
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

        val tareaDAO =  TareasPersonalizacionDAO.new(tarea.id) {
            uuid = tarea.uuid
            precio = tarea.precio
            pedido = pedidoDAO
            peso = tarea.peso
            balance = tarea.balance
            rigidez = tarea.rigidez
        }
    }

    @Test
    fun findAll() {
        val res = tareasPersonalizacionRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() = transaction {
        saveData()

        val res = tareasPersonalizacionRepository.findById(tarea.id)

        assert(res == tarea)
    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<IllegalStateException> {
            val res = tareasPersonalizacionRepository.findById(-5)
        }
    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(usuario)
        pedidosRepository.save(pedido)
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

    @Test
    fun saveUpdate() = transaction {
        saveData()

        val res = tareasPersonalizacionRepository.save(tarea)

        assert(res == tarea)
    }

    @Test
    fun delete() = transaction {
        saveData()

        val res = tareasPersonalizacionRepository.delete(tarea)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = tareasPersonalizacionRepository.delete(tarea)

        assert(!res)
    }
}