package repositories

import config.AppConfig
import db.DataBaseManager
import entities.PedidosDAO
import entities.ProductosDAO
import entities.UsuariosDAO
import exceptions.ProductoException
import models.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import repositories.pedido.PedidosRepository
import repositories.producto.ProductosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProductosRepositoryTest {

    private val productosRepository = ProductosRepository(ProductosDAO, PedidosDAO)
    private val pedidosRepository = PedidosRepository(PedidosDAO, UsuariosDAO)
    private val usuariosRepository = UsuariosRepository(UsuariosDAO)

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

    private val producto = Producto(
        id = -1,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.COMPLEMENTO,
        marca = "Test",
        modelo = "Test",
        precio = 00.0f,
        stock = -1,
        pedido = pedido
    )

    @AfterEach
    fun tearDown() {
        DataBaseManager.dropTables()
    }

    @BeforeEach
    fun beforeEach() {
        DataBaseManager.init(AppConfig.DEFAULT)
        DataBaseManager.clearTables()
    }

    private fun saveData() = transaction {
        val usuarioDAO = UsuariosDAO.new(pedido.encordador.id) {
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

        ProductosDAO.new(producto.id) {
            uuid = producto.uuid
            tipoProducto = producto.tipoProducto
            marca = producto.marca
            modelo = producto.modelo
            precio = producto.precio
            stock = producto.stock
            pedido = pedidoDAO
        }
    }

    @Test
    fun findAll() {
        val res = productosRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() = transaction {
        saveData()

        val res = productosRepository.findById(producto.id)

        assert(res == producto)
    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<ProductoException> {
            val res = productosRepository.findById(-5)
        }

    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(usuario)
        pedidosRepository.save(pedido)
        val res = productosRepository.save(producto)

        assertAll(
            { assertEquals(res.id, producto.id) },
            { assertEquals(res.uuid, producto.uuid) },
            { assertEquals(res.tipoProducto, producto.tipoProducto) },
            { assertEquals(res.marca, producto.marca) },
            { assertEquals(res.modelo, producto.modelo) },
            { assertEquals(res.precio, producto.precio) },
            { assertEquals(res.stock, producto.stock) }
        )
    }

    @Test
    fun saveUpdate() = transaction {
        saveData()

        val res = productosRepository.save(producto)

        assert(res == producto)
    }

    @Test
    fun delete() = transaction {
        saveData()

        val res = productosRepository.delete(producto)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = productosRepository.delete(producto)

        assert(!res)
    }
}