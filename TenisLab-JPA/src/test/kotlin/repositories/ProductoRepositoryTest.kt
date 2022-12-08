package repositories

import db.HibernateManager
import exceptions.ProductoException
import models.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import repositories.pedido.PedidosRepository
import repositories.producto.ProductosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProductoRepositoryTest {

    private val productosRepository = ProductosRepository()
    private val usuariosRepository = UsuariosRepository()
    private val pedidosRepository = PedidosRepository()

    private val usuario = Usuario(
        id = 1,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test@Test.com",
        contrasena = "Test",
        perfil = TipoUsuario.ENCORDADOR
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

    @AfterAll
    fun tearDown() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM PRODUCTO")
            query.executeUpdate()
        }
    }

    @BeforeEach
    fun beforeEach() {
        HibernateManager.transaction {
            val query = HibernateManager.manager.createNativeQuery("DELETE FROM PRODUCTO")
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
        val res = productosRepository.findAll()

        assert(res.isEmpty())
    }

    @Test
    fun findById(){
        productosRepository.save(producto)

        val res = productosRepository.findById(producto.id)

        assert(res == producto)
    }

    @Test
    fun findByIdNoExiste() {
        val res = productosRepository.findById(-5)

        assert(res == null)
    }

    @Test
    fun saveInsert() {
        val res = productosRepository.save(producto)

        assertAll(
            { Assertions.assertEquals(res.id, producto.id) },
            { Assertions.assertEquals(res.uuid, producto.uuid) },
            { Assertions.assertEquals(res.tipoProducto, producto.tipoProducto) },
            { Assertions.assertEquals(res.marca, producto.marca) },
            { Assertions.assertEquals(res.modelo, producto.modelo) },
            { Assertions.assertEquals(res.precio,producto.precio) },
            { Assertions.assertEquals(res.stock,producto.stock) },
            { Assertions.assertEquals(res.pedido,producto.pedido) },
        )
    }

    @Test
    fun delete(){
        productosRepository.save(producto)

        val res = productosRepository.delete(producto)

        assert(res)
    }

    @Test
    fun deleteNoExiste() {
        val res = productosRepository.delete(producto)

        assert(!res)
    }
}