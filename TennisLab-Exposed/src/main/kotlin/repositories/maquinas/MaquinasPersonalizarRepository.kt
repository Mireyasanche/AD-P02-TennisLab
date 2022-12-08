package repositories.maquinas

import entities.TurnosDAO
import entities.UsuariosDAO
import entities.maquinas.MaquinasPersonalizarDAO
import exceptions.maquinas.MaquinaPersonalizarException
import mappers.maquinas.fromMaquinaPersonalizarDAOToMaquinaPersonalizar
import models.maquinas.MaquinaPersonalizar
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.turno.TurnosRepository

class MaquinasPersonalizarRepository(
    private val maquinasPersonalizarDAO: IntEntityClass<MaquinasPersonalizarDAO>,
    private val turnosDAO: IntEntityClass<TurnosDAO>

) : IMaquinasPersonalizarRepository {
    private val turnosRepository = TurnosRepository(turnosDAO, UsuariosDAO)
    private val logger = KotlinLogging.logger {}

    override fun findAll(): List<MaquinaPersonalizar> = transaction {
        logger.debug { "findAll() - buscando todos" }
        maquinasPersonalizarDAO.all().map { it.fromMaquinaPersonalizarDAOToMaquinaPersonalizar() }
    }

    override fun findById(id: Int): MaquinaPersonalizar = transaction {
        logger.debug { "findById($id) - buscando $id" }
        maquinasPersonalizarDAO.findById(id)
            ?.fromMaquinaPersonalizarDAOToMaquinaPersonalizar()
            ?: throw MaquinaPersonalizarException("Máquina de personalizar no encontrada con id: $id")
    }

    override fun save(entity: MaquinaPersonalizar): MaquinaPersonalizar = transaction {
        val existe = maquinasPersonalizarDAO.findById(entity.id)

        val encordadorParaAsignar = entity.turno.encordador
        val encordadoresAsignados = turnosRepository.findAll().filter { it.id == entity.turno.id}.map { it.encordador}
        require(!encordadoresAsignados.contains(encordadorParaAsignar))
        {"Esta máquina no ha podido añadirse porque el encordador que quiere asignarle ya esta utilizando otra máquina en ese turno."}

        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    override fun delete(entity: MaquinaPersonalizar): Boolean = transaction {
        val existe = maquinasPersonalizarDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

    private fun insert(entity: MaquinaPersonalizar): MaquinaPersonalizar {
        logger.debug { "save($entity) - creando" }
        return maquinasPersonalizarDAO.new(entity.id) {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            turno = turnosDAO.findById(entity.turno.id)!!
            mideManiobrabilidad = entity.mideManiobrabilidad
            balance = entity.balance
            rigidez = entity.rigidez

        }.fromMaquinaPersonalizarDAOToMaquinaPersonalizar()
    }

    private fun update(entity: MaquinaPersonalizar, existe: MaquinasPersonalizarDAO): MaquinaPersonalizar {
        logger.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numeroSerie = entity.numeroSerie
            turno = turnosDAO.findById(entity.turno.id)!!
            mideManiobrabilidad = entity.mideManiobrabilidad
            balance = entity.balance
            rigidez = entity.rigidez

        }.fromMaquinaPersonalizarDAOToMaquinaPersonalizar()
    }
}