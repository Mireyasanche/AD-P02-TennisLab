package controllers

import entities.TurnosDAO
import entities.UsuariosDAO
import models.Turno
import repositories.turno.TurnosRepository

class TurnosController(
    private val turnosRepository: TurnosRepository = TurnosRepository(TurnosDAO, UsuariosDAO)
) {
    fun getTurnos(): List<Turno> {
        return turnosRepository.findAll()
    }

    fun getTurnoById(id: Int): Turno {
        return turnosRepository.findById(id)
    }

    fun saveTurno(turno: Turno): Turno {
        return turnosRepository.save(turno)
    }

    fun deleteTurno(turno: Turno): Boolean {
        return turnosRepository.delete(turno)
    }
}