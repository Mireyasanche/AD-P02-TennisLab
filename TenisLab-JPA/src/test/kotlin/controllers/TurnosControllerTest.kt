package controllers

import db.getUsuariosInit
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Turno
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import repositories.turno.TurnosRepository
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
internal class TurnosControllerTest {
    @MockK
    lateinit var turnosRepository: TurnosRepository

    @InjectMockKs
    lateinit var turnosController: TurnosController

    private val turno = Turno(
        id = 0,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 15, 20, 30),
        final = LocalDateTime.of(2022, 12, 31, 9, 15),
        encordador = getUsuariosInit()[2]
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getTurnos() {
        every { turnosRepository.findAll() } returns listOf(turno)

        val res = turnosController.getTurnos()

        assert(res == listOf(turno))

        verify(exactly = 1) { turnosRepository.findAll() }
    }

    @Test
    fun getTurnoById() {
        every { turnosRepository.findById(turno.id) } returns turno

        val res = turnosController.getTurnoById(turno.id)

        assert(res == turno)

        verify(exactly = 1) { turnosRepository.findById(turno.id) }
    }

    @Test
    fun getByIdNoExiste() {
        every { turnosRepository.findById(turno.id) } returns null

        val res = turnosController.getTurnoById(turno.id)
        assert(res == null)

        verify(exactly = 1) { turnosRepository.findById(turno.id) }
    }

    @Test
    fun save() {
        every { turnosRepository.save(turno) } returns turno

        val res = turnosController.saveTurno(turno)

        assert(res == turno)

        verify(exactly = 1) { turnosRepository.save(turno) }
    }

    @Test
    fun delete() {
        every { turnosRepository.delete(turno) } returns true

        val res = turnosController.deleteTurno(turno)

        assert(res)

        verify(exactly = 1) { turnosRepository.delete(turno) }
    }


    @Test
    fun deleteNoExiste() {
        every { turnosRepository.delete(turno) } returns false

        val res = turnosController.deleteTurno(turno)
        assert(!res)

        verify(exactly = 1) { turnosRepository.delete(turno) }
    }
}