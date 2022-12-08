package repositories.tareas

import db.HibernateManager
import models.Pedido
import models.TipoEstado
import models.TipoUsuario
import models.Usuario
import models.tareas.TareaPersonalizacion
import org.junit.jupiter.api.*
import repositories.pedido.PedidosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TareasPersonalizacionRepositoryTest {

    private val tareasPersonalizacionRepository = TareasPersonalizacionRepository()
    private val usuariosRepository = UsuariosRepository()
    private val pedidosRepository = PedidosRepository()

    private val usuario = Usuario(
        id = 3,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.ENCORDADOR
    )

    private val pedido = Pedido(
        id = 3,
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
        id = 3,
        uuid = UUID.randomUUID(),
        precio = 0.0f,
        pedido = pedido,
        peso = 0.0f,
        balance = 0.0f,
        rigidez = 0.0f
    )

    @AfterEach
    fun tearDown() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM TAREAS_PERSONALIZACION")
            query.executeUpdate()
        }
    }

    @BeforeEach
    fun beforeEach() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM TAREAS_PERSONALIZACION")
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
        val res = tareasPersonalizacionRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() {
        tareasPersonalizacionRepository.save(tarea)

        val res = tareasPersonalizacionRepository.findById(tarea.id)

        assert(res == tarea)
    }

    @Test
    fun findByIdNoExiste() {
        val res = tareasPersonalizacionRepository.findById(-5)

        assert(res == null)

    }

    @Test
    fun saveInsert() {
        val res = tareasPersonalizacionRepository.save(tarea)

        assertAll(
            { Assertions.assertEquals(res.id, tarea.id) },
            { Assertions.assertEquals(res.uuid, tarea.uuid) },
            { Assertions.assertEquals(res.precio, tarea.precio) },
            { Assertions.assertEquals(res.balance, tarea.balance) },
            { Assertions.assertEquals(res.rigidez, tarea.rigidez) },
            { Assertions.assertEquals(res.peso, tarea.peso) }
        )
    }

    @Test
    fun delete() {
        tareasPersonalizacionRepository.save(tarea)

        val res = tareasPersonalizacionRepository.delete(tarea)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = tareasPersonalizacionRepository.delete(tarea)

        assert(!res)
    }
}