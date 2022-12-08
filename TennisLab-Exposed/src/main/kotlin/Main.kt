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

    getUsuariosInit().forEach { usuario ->
        usuariosController.saveUsuario(usuario)
    }

    getTurnosInit().forEach { turno ->
        turnosController.saveTurno(turno)
    }

    getPedidosInit().forEach { pedido ->
        pedidosControllers.savePedido(pedido)
    }

    getProductosInit().forEach { producto ->
        productosController.saveProducto(producto)
    }

    getTareasEncordadoInit().forEach { tarea ->
        tareasController.saveTareaEncordado(tarea)
    }

    getTareasPersonalizacion().forEach { tarea ->
        tareasController.saveTareaPersonalizacion(tarea)
    }

    getMaquinasEncordar().forEach { maquina ->
        maquinasControllers.saveMaquinaEncordar(maquina)
    }

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

fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    DataBaseManager.init(appConfig)

}