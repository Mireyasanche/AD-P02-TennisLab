package controllers

import models.maquinas.MaquinaEncordar
import models.maquinas.MaquinaPersonalizar
import repositories.maquinas.MaquinasEncordarRepository
import repositories.maquinas.MaquinasPersonalizarRepository

class MaquinasController(
    private val maquinasEncordarRepository: MaquinasEncordarRepository = MaquinasEncordarRepository(),
    private val maquinasPersonalizarRepository: MaquinasPersonalizarRepository = MaquinasPersonalizarRepository()

){

    fun getMaquinasEncordar(): List<MaquinaEncordar> {
        return maquinasEncordarRepository.findAll()
    }

    fun getMaquinaEncordar(id: Int): MaquinaEncordar? {
        return maquinasEncordarRepository.findById(id)
    }

    fun saveMaquinaEncordar(entity: MaquinaEncordar): MaquinaEncordar {
        return maquinasEncordarRepository.save(entity)
    }

    fun deleteMaquinaEncordar(entity: MaquinaEncordar): Boolean {
        return maquinasEncordarRepository.delete(entity)
    }

    fun getMaquinasPersonalizar(): List<MaquinaPersonalizar> {
        return maquinasPersonalizarRepository.findAll()
    }

    fun getMaquinaPersonalizar(id: Int): MaquinaPersonalizar? {
        return maquinasPersonalizarRepository.findById(id)
    }

    fun saveMaquinaPersonalizar(entity: MaquinaPersonalizar): MaquinaPersonalizar {
        return maquinasPersonalizarRepository.save(entity)
    }

    fun deleteMaquinaPersonalizar(entity: MaquinaPersonalizar): Boolean {
        return maquinasPersonalizarRepository.delete(entity)
    }

}