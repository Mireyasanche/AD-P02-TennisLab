package controllers

import exceptions.PedidoException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Pedido
import models.TipoEstado
import models.TipoUsuario
import models.Usuario
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.pedido.PedidosRepository
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
class PedidosControllerTest {
    @MockK
    lateinit var pedidosRepository: PedidosRepository

    @InjectMockKs
    lateinit var pedidosController: PedidosController

    private val usuario = Usuario(
        id = 0,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test",
        contrasena = "Test",
        perfil = TipoUsuario.TENISTA
    )

    private val pedido = Pedido(
        id = 0,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.TERMINADO,
        encordador = usuario,
        fechaTope = LocalDate.of(2022, 12, 15),
        fechaEntrada = LocalDate.of(2022, 11, 10),
        fechaProgramada = LocalDate.of(2022, 12, 7),
        fechaEntrega = LocalDate.of(2022, 12, 7),
        precio = 10.0f
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getPedidos() {
        every { pedidosRepository.findAll() } returns listOf(pedido)

        val res = pedidosController.getPedidos()

        assert(res == listOf(pedido))

        verify(exactly = 1) { pedidosRepository.findAll() }
    }

    @Test
    fun getPedidoById() {
        every { pedidosRepository.findById(pedido.id) } returns pedido

        val res = pedidosController.getPedido(pedido.id)

        assert(res == pedido)

        verify(exactly = 1) { pedidosRepository.findById(pedido.id) }
    }

    @Test
    fun getByIdNoExiste() {
        every { pedidosRepository.findById(pedido.id) } returns null

        val res = pedidosController.getPedido(pedido.id)
        assert(res == null)

        verify(exactly = 1) { pedidosRepository.findById(pedido.id) }
    }

    @Test
    fun save() {
        every { pedidosRepository.save(pedido) } returns pedido

        val res = pedidosController.savePedido(pedido)

        assert(res == pedido)

        verify(exactly = 1) { pedidosRepository.save(pedido) }
    }

    @Test
    fun delete() {
        every { pedidosRepository.delete(pedido) } returns true

        val res = pedidosController.deletePedido(pedido)

        assert(res)

        verify(exactly = 1) { pedidosRepository.delete(pedido) }
    }


    @Test
    fun deleteNoExiste() {
        every { pedidosRepository.delete(pedido) } returns false

        val res = pedidosController.deletePedido(pedido)
        assert(!res)

        verify(exactly = 1) { pedidosRepository.delete(pedido) }
    }
}