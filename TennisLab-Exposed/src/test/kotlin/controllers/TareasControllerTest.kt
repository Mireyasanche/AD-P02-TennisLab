package controllers

import db.getPedidosInit
import exceptions.tareas.TareaEncordadoException
import exceptions.tareas.TareaPersonalizacionException
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
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado
import models.tareas.TareaPersonalizacion
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.tareas.TareasEncordadoRepository
import repositories.tareas.TareasPersonalizacionRepository
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
internal class TareasControllerTest {

    @MockK
    lateinit var tareasEncordadoRepository: TareasEncordadoRepository

    @MockK
    lateinit var tareasPersonalizacionRepository: TareasPersonalizacionRepository

    @InjectMockKs
    lateinit var tareasController: TareasController

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

    private val tareaEncordado = TareaEncordado(
        id = -1,
        uuid = UUID.randomUUID(),
        precio = 0.0f,
        pedido = pedido,
        tensionHorizontal = 0.0f,
        cordajeHorizontal = "Test",
        tensionVertical =  0.0f,
        cordajeVertical = "Test",
        nudos = NumeroNudos.CUATRO
    )

    private val tareaPersonalizacion = TareaPersonalizacion(
        id = 0,
        uuid = UUID.randomUUID(),
        precio = 70.0f,
        pedido = getPedidosInit()[1],
        peso = 0.3f,
        balance = 320.0f,
        rigidez = 70.0f
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getAllTareasEncordado() {
        every { tareasEncordadoRepository.findAll() } returns listOf(tareaEncordado)

        val res = tareasController.getAllTareasEncordado()

        assert(res == listOf(tareaEncordado))

        verify(exactly = 1) { tareasEncordadoRepository.findAll() }
    }

    @Test
    fun getTareaEncordadoById() {
        every { tareasEncordadoRepository.findById(tareaEncordado.id) } returns tareaEncordado

        val res = tareasController.getTareaEncordadoById(tareaEncordado.id)

        assert(res == tareaEncordado)

        verify(exactly = 1) { tareasEncordadoRepository.findById(tareaEncordado.id) }
    }

    @Test
    fun getByIdNoExiste() {
        every { tareasEncordadoRepository.findById(tareaEncordado.id) } throws TareaEncordadoException("Tarea no encontrada con id: ${tareaEncordado.id}")

        val res = assertThrows<TareaEncordadoException> {
            tareasController.getTareaEncordadoById(tareaEncordado.id)
        }

        assert(res.message == "Tarea no encontrada con id: ${tareaEncordado.id}")

        verify(exactly = 1) { tareasEncordadoRepository.findById(tareaEncordado.id) }
    }

    @Test
    fun saveTareaEncordado() {
        every { tareasEncordadoRepository.save(tareaEncordado) } returns tareaEncordado

        val res = tareasController.saveTareaEncordado(tareaEncordado)

        assert(res == tareaEncordado)

        verify(exactly = 1) { tareasEncordadoRepository.save(tareaEncordado) }
    }

    @Test
    fun deleteTareaEncordado() {
        every { tareasEncordadoRepository.delete(tareaEncordado) } returns true

        val res = tareasController.deleteTareaEncordado(tareaEncordado)

        assert(res)

        verify(exactly = 1) { tareasEncordadoRepository.delete(tareaEncordado) }
    }

    @Test
    fun deleteNoExiste() {
        every { tareasEncordadoRepository.delete(tareaEncordado) } throws TareaEncordadoException("Tarea no encontrada con id: ${tareaEncordado.id}")

        val res = assertThrows<TareaEncordadoException> { tareasController.deleteTareaEncordado(tareaEncordado) }

        assert(res.message == "Tarea no encontrada con id: ${tareaEncordado.id}")

        verify(exactly = 1) { tareasEncordadoRepository.delete(tareaEncordado) }
    }

    @Test
    fun getAllTareasPersonalizacion() {
        every { tareasPersonalizacionRepository.findAll() } returns listOf(tareaPersonalizacion)

        val res = tareasController.getAllTareasPersonalizacion()

        assert(res == listOf(tareaPersonalizacion))

        verify(exactly = 1) { tareasPersonalizacionRepository.findAll() }
    }

    @Test
    fun getTareaPersonalizacionById() {
        every { tareasPersonalizacionRepository.findById(tareaPersonalizacion.id) } returns tareaPersonalizacion

        val res = tareasController.getTareaPersonalizacionById(tareaPersonalizacion.id)

        assert(res == tareaPersonalizacion)

        verify(exactly = 1) { tareasPersonalizacionRepository.findById(tareaPersonalizacion.id) }
    }

    @Test
    fun getByTareaPersonalizacionIdNoExiste() {
        every { tareasPersonalizacionRepository.findById(tareaPersonalizacion.id) } throws TareaPersonalizacionException("Tarea no encontrada con id: ${tareaPersonalizacion.id}")

        val res = assertThrows<TareaPersonalizacionException> {
            tareasController.getTareaPersonalizacionById(tareaPersonalizacion.id)
        }

        assert(res.message == "Tarea no encontrada con id: ${tareaPersonalizacion.id}")

        verify(exactly = 1) { tareasPersonalizacionRepository.findById(tareaPersonalizacion.id) }
    }

    @Test
    fun saveTareaPersonalizacion() {
        every { tareasPersonalizacionRepository.save(tareaPersonalizacion) } returns tareaPersonalizacion

        val res = tareasController.saveTareaPersonalizacion(tareaPersonalizacion)

        assert(res == tareaPersonalizacion)

        verify(exactly = 1) { tareasPersonalizacionRepository.save(tareaPersonalizacion) }
    }

    @Test
    fun deleteTareaPersonalizacion() {
        every { tareasPersonalizacionRepository.delete(tareaPersonalizacion) } returns true

        val res = tareasController.deleteTareaPersonalizacion(tareaPersonalizacion)

        assert(res)

        verify(exactly = 1) { tareasPersonalizacionRepository.delete(tareaPersonalizacion) }
    }

    @Test
    fun deleteTareaPersonalizacionNoExiste() {
        every { tareasPersonalizacionRepository.delete(tareaPersonalizacion) } throws TareaPersonalizacionException("Tarea no encontrada con id: ${tareaPersonalizacion.id}")

        val res = assertThrows<TareaPersonalizacionException> { tareasController.deleteTareaPersonalizacion(tareaPersonalizacion) }

        assert(res.message == "Tarea no encontrada con id: ${tareaPersonalizacion.id}")

        verify(exactly = 1) { tareasPersonalizacionRepository.delete(tareaPersonalizacion) }
    }
}