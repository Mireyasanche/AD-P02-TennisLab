package repositories

import config.AppConfig
import db.DataBaseManager
import entities.PedidosDAO
import entities.ProductosDAO
import exceptions.ProductoException
import models.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import repositories.producto.ProductosRepository
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProductosRepositoryTest {

    private val productosRepository = ProductosRepository(ProductosDAO, PedidosDAO)

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
        val res = productosRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById() = transaction {
        ProductosDAO.new(producto.id) {
            uuid = producto.uuid
            tipoProducto = producto.tipoProducto
            marca = producto.marca
            modelo = producto.modelo
            precio = producto.precio
            stock = producto.stock
            pedido = producto.pedido as PedidosDAO
        }

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
        val res = productosRepository.save(producto)

        assertAll(
            { assertEquals(res.id, producto.id) },
            { assertEquals(res.uuid, producto.uuid) },
            { assertEquals(res.tipoProducto, producto.tipoProducto) },
            { assertEquals(res.marca, producto.marca) },
            { assertEquals(res.modelo, producto.modelo) },
            { assertEquals(res.precio, producto.precio) },
            { assertEquals(res.stock, producto.stock) },
            { assertEquals(res.pedido, producto.pedido) },
        )
    }

    @Test
    fun saveUpdate() = transaction {
        ProductosDAO.new(producto.id) {
            uuid = producto.uuid
            tipoProducto = producto.tipoProducto
            marca = producto.marca
            modelo = producto.modelo
            precio = producto.precio
            stock = producto.stock
            pedido = producto.pedido as PedidosDAO
        }

        val res = productosRepository.save(producto)

        assert(res == producto)
    }

    @Test
    fun delete() = transaction {
        ProductosDAO.new(producto.id) {
            uuid = producto.uuid
            tipoProducto = producto.tipoProducto
            marca = producto.marca
            modelo = producto.modelo
            precio = producto.precio
            stock = producto.stock
            pedido = producto.pedido as PedidosDAO
        }

        val res = productosRepository.delete(producto)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = productosRepository.delete(producto)

        assert(!res)
    }
}