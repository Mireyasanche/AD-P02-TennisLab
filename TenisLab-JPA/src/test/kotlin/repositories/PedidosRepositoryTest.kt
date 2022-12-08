package repositories

import db.HibernateManager
import models.Pedido
import models.TipoEstado
import models.TipoUsuario
import models.Usuario
import org.junit.jupiter.api.*
import repositories.pedido.PedidosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PedidosRepositoryTest {
    private val pedidosRepository: PedidosRepository = PedidosRepository()
    private val usuariosRepository: UsuariosRepository = UsuariosRepository()

    private val usuario = Usuario(
        id = 4,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.ENCORDADOR
    )

    private val pedido = Pedido(
        id = 4,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.TERMINADO,
        encordador = usuario,
        fechaTope = LocalDate.of(2022, 12, 15),
        fechaEntrada = LocalDate.of(2022, 11, 10),
        fechaProgramada = LocalDate.of(2022, 12, 7),
        fechaEntrega = LocalDate.of(2022, 12, 7),
        precio = 10.0f
    )

    @AfterEach
    fun tearDown() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM TURNOS")
            query.executeUpdate()
        }
    }

    @BeforeEach
    fun beforeEach() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM TURNOS")
            query.executeUpdate()
        }
        HibernateManager.open()
        HibernateManager.close()
    }

    @Test
    fun findAll() {
        val res = pedidosRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() {
        pedidosRepository.save(pedido)

        val res = pedidosRepository.findById(pedido.id)

        assert(res == pedido)
    }

    @Test
    fun findByIdNoExiste() {
        val res = pedidosRepository.findById(-5)

        assert(res == null)

    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(usuario)
        val res = pedidosRepository.save(pedido)

        assertAll(
            { Assertions.assertEquals(res.id, pedido.id) },
            { Assertions.assertEquals(res.uuid, pedido.uuid) },
            { Assertions.assertEquals(res.estado, pedido.estado) },
            { Assertions.assertEquals(res.encordador, pedido.encordador) },
            { Assertions.assertEquals(res.fechaTope, pedido.fechaTope) },
            { Assertions.assertEquals(res.fechaEntrada, pedido.fechaEntrada) },
            { Assertions.assertEquals(res.fechaEntrega, pedido.fechaEntrega) },
            { Assertions.assertEquals(res.fechaTope, pedido.fechaTope) },
            { Assertions.assertEquals(res.fechaProgramada, pedido.fechaProgramada) },
            { Assertions.assertEquals(res.precio, pedido.precio) }
        )
    }

    @Test
    fun delete() {
        usuariosRepository.save(usuario)
        pedidosRepository.save(pedido)

        val res = pedidosRepository.delete(pedido)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = pedidosRepository.delete(pedido)

        assert(!res)
    }
}