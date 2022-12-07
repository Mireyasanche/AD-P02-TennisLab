package controllers.maquinas

import controllers.MaquinasController
import exceptions.maquinas.MaquinaEncordarException
import exceptions.maquinas.MaquinaPersonalizarException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.*
import models.maquinas.MaquinaEncordar
import models.maquinas.MaquinaPersonalizar
import models.maquinas.TipoEncordaje
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.maquinas.MaquinasEncordarRepository
import repositories.maquinas.MaquinasPersonalizarRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
internal class MaquinasControllerTest {
    @MockK
    lateinit var maquinasPersonalizarRepository: MaquinasPersonalizarRepository
    @MockK
    lateinit var maquinasEncordarRepository: MaquinasEncordarRepository

    @InjectMockKs
    lateinit var maquinasController: MaquinasController

    private val usuario = Usuario(
        id = 0,
        uuid = UUID.randomUUID(),
        nombre = "Test",
        apellido = "Test",
        email = "Test",
        contrasena = "Test",
        perfil = TipoUsuario.TENISTA
    )

    private val turno = Turno(
        id = 0,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 15, 20, 30),
        final = LocalDateTime.of(2022, 12, 31, 9, 15),
        encordador = usuario
    )

    private val maquinaEncordar = MaquinaEncordar(
        id = 0,
        uuid = UUID.randomUUID(),
        marca = "Test",
        modelo = "Test",
        fechaAdquisicion = LocalDate.of(2021, 11, 19),
        numeroSerie = 0,
        turno = turno,
        tipo = TipoEncordaje.AUTOMATICA,
        tensionMaxima = 00.0f,
        tensionMinima = 00.0f
    )

    private val maquinaPersonalizar = MaquinaPersonalizar(
        id = 0,
        uuid = UUID.randomUUID(),
        marca = "Test",
        modelo = "Test",
        fechaAdquisicion = LocalDate.of(2021, 11, 19),
        numeroSerie = 0,
        turno = turno,
        mideManiobrabilidad = false,
        balance = 00.0f,
        rigidez = 00.0f
    )

    @Test
    fun getMaquinasPersonalizar() {
        every { maquinasPersonalizarRepository.findAll() } returns listOf(maquinaPersonalizar)

        val res = maquinasController.getMaquinasPersonalizar()

        assert(res == listOf(maquinaPersonalizar))

        verify(exactly = 1) { maquinasPersonalizarRepository.findAll() }
    }

    @Test
    fun getMaquinaPersonalizarById() {
        every { maquinasPersonalizarRepository.findById(maquinaPersonalizar.id) } returns maquinaPersonalizar

        val res = maquinasController.getMaquinaPersonalizar(maquinaPersonalizar.id)

        assert(res == maquinaPersonalizar)

        verify(exactly = 1) { maquinasPersonalizarRepository.findById(maquinaPersonalizar.id) }
    }

    @Test
    fun getMaquinaPersonzalizarByIdNoExiste() {
        every { maquinasPersonalizarRepository.findById(maquinaPersonalizar.id) } throws MaquinaPersonalizarException("Máquina con id ${maquinaPersonalizar.id} no existe")

        val res = assertThrows<MaquinaPersonalizarException> { maquinasController.getMaquinaPersonalizar(maquinaPersonalizar.id) }

        assert(res.message == "Máquina con id ${maquinaPersonalizar.id} no existe")

        verify(exactly = 1) { maquinasPersonalizarRepository.findById(maquinaPersonalizar.id) }
    }

    @Test
    fun saveMaquinaPersonalizar() {
        every { maquinasPersonalizarRepository.save(maquinaPersonalizar) } returns maquinaPersonalizar

        val res = maquinasController.saveMaquinaPersonalizar(maquinaPersonalizar)

        assert(res == maquinaPersonalizar)

        verify(exactly = 1) { maquinasPersonalizarRepository.save(maquinaPersonalizar) }
    }

    @Test
    fun deleteMaquinaPersonalizar() {
        every { maquinasPersonalizarRepository.delete(maquinaPersonalizar) } returns true

        val res = maquinasController.deleteMaquinaPersonalizar(maquinaPersonalizar)

        assert(res)

        verify(exactly = 1) { maquinasPersonalizarRepository.delete(maquinaPersonalizar) }
    }


    @Test
    fun deleteMaquinaPersonalizarNoExiste() {
        every { maquinasPersonalizarRepository.delete(maquinaPersonalizar) } throws MaquinaPersonalizarException("Máquina con id ${maquinaPersonalizar.id} no existe")

        val res = assertThrows<MaquinaPersonalizarException> { maquinasController.deleteMaquinaPersonalizar(maquinaPersonalizar) }

        assert(res.message == "Máquina con id ${maquinaPersonalizar.id} no existe")

        verify(exactly = 1) { maquinasPersonalizarRepository.delete(maquinaPersonalizar) }
    }

    @Test
    fun getMaquinaEncordado() {
        every { maquinasEncordarRepository.findAll() } returns listOf(maquinaEncordar)

        val res = maquinasController.getMaquinasEncordar()

        assert(res == listOf(maquinaEncordar))

        verify(exactly = 1) { maquinasEncordarRepository.findAll() }
    }

    @Test
    fun getMaquinaEncordarById() {
        every { maquinasEncordarRepository.findById(maquinaEncordar.id) } returns maquinaEncordar

        val res = maquinasController.getMaquinaEncordar(maquinaEncordar.id)

        assert(res == maquinaEncordar)

        verify(exactly = 1) { maquinasEncordarRepository.findById(maquinaEncordar.id) }
    }

    @Test
    fun getByIdNoExiste() {
        every { maquinasEncordarRepository.findById(maquinaEncordar.id) } throws MaquinaEncordarException("Máquina con id ${maquinaEncordar.id} no existe")

        val res = assertThrows<MaquinaEncordarException> { maquinasController.getMaquinaEncordar(maquinaEncordar.id) }

        assert(res.message == "Máquina con id ${maquinaEncordar.id} no existe")

        verify(exactly = 1) { maquinasEncordarRepository.findById(maquinaEncordar.id) }
    }

    @Test
    fun saveMaquinaEncordar() {
        every { maquinasEncordarRepository.save(maquinaEncordar) } returns maquinaEncordar

        val res = maquinasController.saveMaquinaEncordar(maquinaEncordar)

        assert(res == maquinaEncordar)

        verify(exactly = 1) { maquinasEncordarRepository.save(maquinaEncordar) }
    }

    @Test
    fun delete() {
        every { maquinasEncordarRepository.delete(maquinaEncordar) } returns true

        val res = maquinasController.deleteMaquinaEncordar(maquinaEncordar)

        assert(res)

        verify(exactly = 1) { maquinasEncordarRepository.delete(maquinaEncordar) }
    }


    @Test
    fun deleteNoExiste() {
        every { maquinasEncordarRepository.delete(maquinaEncordar) } throws MaquinaEncordarException("Máquina con id ${maquinaEncordar.id} no existe")

        val res = assertThrows<MaquinaEncordarException> { maquinasController.deleteMaquinaEncordar(maquinaEncordar) }

        assert(res.message == "Máquina con id ${maquinaEncordar.id} no existe")

        verify(exactly = 1) { maquinasEncordarRepository.delete(maquinaEncordar) }
    }
}