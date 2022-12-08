package repositories

import config.AppConfig
import db.DataBaseManager
import db.getUsuariosInit
import entities.PedidosDAO
import entities.UsuariosDAO
import exceptions.PedidoException
import models.Pedido
import models.TipoEstado
import org.junit.jupiter.api.*
import repositories.pedido.PedidosRepository
import repositories.usuario.UsuariosRepository
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PedidosRepositoryTest {
    private val pedidosRepository: PedidosRepository = PedidosRepository(PedidosDAO, UsuariosDAO)
    private val usuariosRepository: UsuariosRepository = UsuariosRepository(UsuariosDAO)

    private val pedido = Pedido(
        id = 0,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.TERMINADO,
        encordador = getUsuariosInit()[2],
        fechaTope = LocalDate.of(2022, 12, 15),
        fechaEntrada = LocalDate.of(2022, 11, 10),
        fechaProgramada = LocalDate.of(2022, 12, 7),
        fechaEntrega = LocalDate.of(2022, 12, 7),
        precio = 10.0f
    )

    @BeforeAll
    fun setUp() {
        DataBaseManager.init(AppConfig.DEFAULT)
    }

    @AfterAll
    fun tearDown() {
        DataBaseManager.dropTables()
    }

    @BeforeEach
    fun beforeEach() {
        DataBaseManager.clearTables()
    }

    @Test
    fun findAll() {
        val res = pedidosRepository.findAll()

        assert(res.isEmpty())
    }

//    @Test
//    fun findById() = transaction {
//        PedidosDAO.new(pedido.id) {
//            uuid = pedido.uuid
//            estado = pedido.estado.toString()
//            encordador = pedido.encordador
//            fechaTope = pedido.fechaTope
//            fechaEntrada = pedido.fechaEntrada
//            fechaProgramada = pedido.fechaProgramada
//            fechaEntrega = pedido.fechaEntrega
//            precio = precio
//        }
//
//        val res = pedidosRepository.findById(pedido.id)
//        assert(res == pedido)
//    }

    @Test
    fun findByIdNoExiste() {
        assertThrows<PedidoException> {
            pedidosRepository.findById(pedido.id)
        }
    }

    @Test
    fun saveInsert() {
        usuariosRepository.save(getUsuariosInit()[2])
        val res = pedidosRepository.save(pedido)

        assert(pedidosRepository.findAll()[0] == res)
    }

//    @Test
//    fun saveUpdate() = transaction {
//        PedidosDAO.new(pedido.id) {
//            uuid = pedido.uuid
//            estado = pedido.estado.toString()
//            encordador = pedido.encordador
//            fechaTope = pedido.fechaTope
//            fechaEntrada = pedido.fechaEntrada
//            fechaProgramada = pedido.fechaProgramada
//            fechaEntrega = pedido.fechaEntrega
//            precio = precio
//        }
//
//        val res = pedidosRepository.findById(pedido.id)
//        assert(res == pedido)
//    }

//    @Test
//    fun delete() = transaction {
//        PedidosDAO.new(pedido.id) {
//            uuid = pedido.uuid
//            estado = pedido.estado.toString()
//            encordador = pedido.encordador
//            fechaTope = pedido.fechaTope
//            fechaEntrada = pedido.fechaEntrada
//            fechaProgramada = pedido.fechaProgramada
//            fechaEntrega = pedido.fechaEntrega
//            precio = precio
//        }
//
//        val res = pedidosRepository.delete(pedido)
//        assert(res)
//    }

    @Test
    fun deleteNoExiste() {
        val res = pedidosRepository.delete(pedido)

        assert(!res)
    }
}