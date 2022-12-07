import config.AppConfig
import controllers.*
import db.DataBaseManager
import db.getUsuariosInit
import dto.*
import models.TipoEstado
import services.StorageJSON

fun main(args: Array<String>) {
    initDataBase()
    val maquinasControllers = MaquinasController()
    val pedidosControllers = PedidosController()
    val productosController = ProductosController()
    val tareasController = TareasController()
    val turnosController = TurnosController()
    val usuariosController = UsuariosController()
    val pedidoJSON = StorageJSON<PedidoDTO>()
    val productoJSON = StorageJSON<ProductoDTO>()
    val turnoJSON = StorageJSON<TurnoDTO>()

    getUsuariosInit().forEach { usuario ->
        usuariosController.saveUsuario(usuario)
    }

    usuariosController.getUsuarios().forEach {  usuario ->
        println(usuario)
    }

    //Información completa en JSON de un pedido.
    val pedido = pedidosControllers.getPedido(1).toDTO()
    pedidoJSON.write("Informacion_completa_pedido", listOf(pedido))

    //Listado de pedidos pendientes en JSON.
    val pedidosPendientes = pedidosControllers
        .getPedidos()
        .filter { it.estado == TipoEstado.EN_PROCESO}
        .map { it.toDTO()}
    pedidoJSON.write("listado_pedidos_pendientes", pedidosPendientes)

    //Listado de pedidos completados en JSON.
    val pedidosCompletados = pedidosControllers
        .getPedidos()
        .stream()
        .filter { it.estado == TipoEstado.TERMINADO}
        .map { it.toDTO()}.toList()
    pedidoJSON.write("listado_pedidos_completados", pedidosCompletados)

    //Listado de productos y servicios que ofrecemos en JSON.
    val productos = productosController
        .getProductos()
        .map { it.toDTO() }
    productoJSON.write("productos_ofrecidos", productos)

    //Listado de asignaciones para los encordadores por fecha en JSON.
    val asignaciones = turnosController
        .getTurnos()
        .sortedBy { it.comienzo }
        .map { it.toDTO()}
    turnoJSON.write("listado_asignaciones_encordadores_por_fecha", asignaciones)

}

fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    DataBaseManager.init(appConfig)

}