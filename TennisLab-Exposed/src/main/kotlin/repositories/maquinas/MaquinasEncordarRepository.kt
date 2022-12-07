package repositories.maquinas

import entities.TurnosDAO
import entities.maquinas.MaquinasEncordarDAO
import exceptions.maquinas.MaquinaEncordarException
import mappers.maquinas.fromMaquinaEncordarDAOToMaquinaEncordar
import models.maquinas.MaquinaEncordar
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class MaquinasEncordarRepository(
    private val maquinasEncordarDAO: IntEntityClass<MaquinasEncordarDAO>,
    private val turnosDAO: IntEntityClass<TurnosDAO>

) : IMaquinasEncordarRepository {
    private val logger = KotlinLogging.logger {}

    override fun findAll(): List<MaquinaEncordar> = transaction {
        logger.debug { "findAll() - buscando todos" }
        maquinasEncordarDAO.all().map { it.fromMaquinaEncordarDAOToMaquinaEncordar() }
    }

    override fun findById(id: Int): MaquinaEncordar = transaction {
        logger.debug { "findById($id) - buscando $id" }
        maquinasEncordarDAO.findById(id)
            ?.fromMaquinaEncordarDAOToMaquinaEncordar()
            ?: throw MaquinaEncordarException("Máquina de encordar no encontrada con id: $id")
    }

    override fun save(entity: MaquinaEncordar): MaquinaEncordar = transaction {
        val existe = maquinasEncordarDAO.findById(entity.id)

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    override fun delete(entity: MaquinaEncordar): Boolean = transaction {
        val existe = maquinasEncordarDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

    private fun insert(entity: MaquinaEncordar): MaquinaEncordar {
        logger.debug { "save($entity) - creando" }
        return maquinasEncordarDAO.new(entity.id) {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            turno = turnosDAO.findById(entity.turno.id)!!
            tipo = entity.tipo.toString()
            tensionMaxima = entity.tensionMaxima
            tensionMinima = entity.tensionMinima

        }.fromMaquinaEncordarDAOToMaquinaEncordar()
    }

    private fun update(entity: MaquinaEncordar, existe: MaquinasEncordarDAO): MaquinaEncordar {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            turno = turnosDAO.findById(entity.turno.id)!!
            tipo = entity.tipo.toString()
            tensionMaxima = entity.tensionMaxima
            tensionMinima = entity.tensionMinima

        }.fromMaquinaEncordarDAOToMaquinaEncordar()
    }
}