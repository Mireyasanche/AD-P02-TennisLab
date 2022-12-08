/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package repositories.maquinas

import entities.TurnosDAO
import entities.UsuariosDAO
import entities.maquinas.MaquinasEncordarDAO
import exceptions.maquinas.MaquinaEncordarException
import mappers.maquinas.fromMaquinaEncordarDAOToMaquinaEncordar
import models.maquinas.MaquinaEncordar
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.turno.TurnosRepository

class MaquinasEncordarRepository(
    private val maquinasEncordarDAO: IntEntityClass<MaquinasEncordarDAO>,
    private val turnosDAO: IntEntityClass<TurnosDAO>

) : IMaquinasEncordarRepository {
    private val logger = KotlinLogging.logger {}
    private val turnosRepository = TurnosRepository(turnosDAO, UsuariosDAO)

    /**
     * Método encargado de ejecutar transacción, con una consulta de los DAO en su interior, la cual se encarga de
     * devolver todos los objetos de un tipo en concreto que estén almacenados en la base de datos
     *
     * @return List<MaquinaEncordar>, la lista de objetos encontrada en su respectiva base de datos convertido de DAO a modelo.
     */
    override fun findAll(): List<MaquinaEncordar> = transaction {
        logger.debug { "findAll() - buscando todos" }
        maquinasEncordarDAO.all().map { it.fromMaquinaEncordarDAOToMaquinaEncordar() }
    }

    /**
     * Método encargado de ejecutar transacción, con una consulta del DAO en su interior, la cual se encarga de
     * devolver el objeto de un tipo en concreto que tiene un identificador específico.
     *
     * @param id identificador de tipo Integer del objeto a consultar.
     *
     * @throws MaquinaEncordarException si la maquina no se encuentra.
     *
     * @return MaquinaEncordar, el objeto que tiene el identificador introducido convertido de DAO a modelo.
     */
    override fun findById(id: Int): MaquinaEncordar = transaction {
        logger.debug { "findById($id) - buscando $id" }
        maquinasEncordarDAO.findById(id)
            ?.fromMaquinaEncordarDAOToMaquinaEncordar()
            ?: throw MaquinaEncordarException("Máquina de encordar no encontrada con id: $id")
    }

    /**
     * Método encargado de ejecutar transacción, con una inserción del DAO a la base de datos en su interior.
     * Si existe el objeto a insertar, lo actualizará. En caso contrario simplemente hará la inserción de un nuevo objeto.
     * Ademas será necesario que el objeto en cuestión cuente con un encordador que no esté utilizando ya otra máquina en ese mismo turno
     * para poder añadirse sin problemas.
     *
     * @param entity objeto a insetar o actualizar en la base de datos.
     *
     * @return MaquinaEncordar, el objeto que ha sido insertado o actualizado convertido de DAO a modelo.
     */
    override fun save(entity: MaquinaEncordar): MaquinaEncordar = transaction {
        val existe = maquinasEncordarDAO.findById(entity.id)

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

    /**
     * Método encargado de ejecutar transacción, con un borrado del DAO de la base de datos en su interior.
     *
     * @param entity objeto a borrar en la base de datos.
     *
     * @return Boolean, true en caso de que se haya podido borrar el objeto, false si no se ha podido encontrar y por lo tanto borrar convertido de DAO a modelo.
     */
    override fun delete(entity: MaquinaEncordar): Boolean = transaction {
        val existe = maquinasEncordarDAO.findById(entity.id) ?: return@transaction false
        logger.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

    /**
     * Método encargado de realizar la inserción del DAO en caso de que la entidad a introducir no exista en la base de datos.
     *
     * @param entity objeto a insetar.
     *
     * @return MaquinaEncordar, el objeto que ha sido insertado convertido de DAO a modelo.
     */
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

    /**
     * Método encargado de realizar la actualización del DAO en caso de que la entidad a introducir exista en la base de datos.
     *
     * @param entity objeto a actualizar.
     * @param existe objeto DAO si existe, nulo si no.
     *
     * @return MaquinaEncordar, el objeto que ha sido actualizado convertido de DAO a modelo.
     */
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