package controllers

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.producto.ProductosRepository
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
internal class ProductosControllerTest {

    @MockK
    lateinit var productosRepository: ProductosRepository

    @InjectMockKs
    lateinit var productosController: ProductosController
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

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getProductos() {
        every { productosRepository.findAll() } returns listOf(producto)

        val res = productosController.getProductos()

        assert(res == listOf(producto))

        verify(exactly = 1) { productosRepository.findAll() }
    }

    @Test
    fun testGetProductos() {
        every { productosRepository.findById(producto.id) } returns producto

        val res = productosController.getProductos(producto.id)

        assert(res == producto)

        verify(exactly = 1) { productosRepository.findById(producto.id) }
    }

    @Test
    fun getByIdNoExiste() {
        every { productosRepository.findById(producto.id) } returns null

        val res = productosController.getProductos(producto.id)

        assert(res == null)

        verify(exactly = 1) { productosRepository.findById(producto.id) }
    }

    @Test
    fun saveProductos() {
        every { productosRepository.save(producto) } returns producto

        val res = productosController.saveProductos(producto)

        assert(res == producto)

        verify(exactly = 1) { productosRepository.save(producto) }
    }

    @Test
    fun deleteProductos() {
        every { productosRepository.delete(producto) } returns true

        val res = productosController.deleteProductos(producto)

        assert(res)

        verify(exactly = 1) { productosRepository.delete(producto) }
    }

    @Test
    fun deleteNoExiste() {
        every { productosRepository.delete(producto) } returns false

        val res = productosController.deleteProductos(producto)

        assert(!res)

        verify(exactly = 1) { productosRepository.delete(producto) }
    }
}