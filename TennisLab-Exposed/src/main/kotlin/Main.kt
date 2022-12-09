/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
import config.AppConfig
import controllers.*
import db.*
import dto.toDTO
import models.TipoEstado
import services.StorageJSON

fun main() {
    initDataBase()
    val maquinasControllers = MaquinasController()
    val pedidosControllers = PedidosController()
    val productosController = ProductosController()
    val tareasController = TareasController()
    val turnosController = TurnosController()
    val usuariosController = UsuariosController()
    val serviceJSON = StorageJSON()

    //Inserción de los datos de usuarios en la base de datos
    getUsuariosInit().forEach { usuario ->
        usuariosController.saveUsuario(usuario)
    }

    //Inserción de los datos de turnos en la base de datos
    getTurnosInit().forEach { turno ->
        turnosController.saveTurno(turno)
    }

    //Inserción de los datos de pedidos en la base de datos
    getPedidosInit().forEach { pedido ->
        pedidosControllers.savePedido(pedido)
    }

    //Inserción de los datos de productos en la base de datos
    getProductosInit().forEach { producto ->
        productosController.saveProducto(producto)
    }

    //Inserción de los datos de tareas de encordado en la base de datos
    getTareasEncordadoInit().forEach { tarea ->
        tareasController.saveTareaEncordado(tarea)
    }

    //Inserción de los datos de tareas de personalización en la base de datos
    getTareasPersonalizacion().forEach { tarea ->
        tareasController.saveTareaPersonalizacion(tarea)
    }

    //Inserción de los datos de máquinas de encordado en la base de datos
    getMaquinasEncordar().forEach { maquina ->
        maquinasControllers.saveMaquinaEncordar(maquina)
    }

    //Inserción de los datos de máquinas de personalización en la base de datos
    getMaquinasPersonalizar().forEach { maquina ->
        maquinasControllers.saveMaquinaPersonalizar(maquina)
    }

    //Información completa en JSON de un pedido.
    val pedido = pedidosControllers.getPedido(1).toDTO()
    serviceJSON.writePedido("Informacion_completa_pedido", listOf(pedido))

    //Listado de pedidos pendientes en JSON.
    val pedidosPendientes = pedidosControllers
        .getPedidos()
        .filter { it.estado == TipoEstado.EN_PROCESO }
        .map { it.toDTO() }
    serviceJSON.writePedido("listado_pedidos_pendientes", pedidosPendientes)

    //Listado de pedidos completados en JSON.
    val pedidosCompletados = pedidosControllers
        .getPedidos()
        .stream()
        .filter { it.estado == TipoEstado.TERMINADO }
        .map { it.toDTO() }.toList()
    serviceJSON.writePedido("listado_pedidos_completados", pedidosCompletados)

    //Listado de productos y servicios que ofrecemos en JSON.
    val productos = productosController
        .getProductos()
        .map { it.toDTO() }
    serviceJSON.writeProducto("productos_ofrecidos", productos)

    //Listado de asignaciones para los encordadores por fecha en JSON.
    val asignaciones = turnosController
        .getTurnos()
        .sortedBy { it.comienzo }
        .map { it.toDTO() }
    serviceJSON.writeTurno("listado_asignaciones_encordadores_por_fecha", asignaciones)

}

/**
 * Método encargado de iniciar la base de datos y configurar todo en base al fichero ApplicationProperties
 *
 *
 * @return Unit
 */
fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    DataBaseManager.init(appConfig)

}