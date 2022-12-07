package db

import models.*
import models.maquinas.MaquinaEncordar
import models.maquinas.MaquinaPersonalizar
import models.maquinas.TipoEncordaje
import models.tareas.NumeroNudos
import models.tareas.TareaEncordado
import models.tareas.TareaPersonalizacion
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

fun getUsuariosInit() = listOf(
    Usuario(
        id = 0,
        uuid = UUID.randomUUID(),
        nombre = "Juan",
        apellido = "López",
        email = "juan@lopez.com",
        contrasena = "1234",
        perfil = TipoUsuario.TENISTA
    ),
    Usuario(
        id = 1,
        uuid = UUID.randomUUID(),
        nombre = "Roberto",
        apellido = "Gómez",
        email = "roberto@gomez.com",
        contrasena = "1234",
        perfil = TipoUsuario.ADMINISTRADOR_ENCARGADO
    ),
    Usuario(
        id = 2,
        uuid = UUID.randomUUID(),
        nombre = "María",
        apellido = "Sanz",
        email = "maria@sanz.com",
        contrasena = "1234",
        perfil = TipoUsuario.ENCORDADOR
    ),
    Usuario(
        id = 3,
        uuid = UUID.randomUUID(),
        nombre = "José",
        apellido = "Vázquez",
        email = "jose@vazquez.com",
        contrasena = "1234",
        perfil = TipoUsuario.ADMINISTRADOR_JEFE
    ),
    Usuario(
        id = 4,
        uuid = UUID.randomUUID(),
        nombre = "Ana",
        apellido = "Carlos",
        email = "ana@carlos.com",
        contrasena = "1234",
        perfil = TipoUsuario.ENCORDADOR
    )
)

fun getTurnosInit() = listOf(
    Turno(
        id = 0,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 7, 17, 48),
        final = LocalDateTime.of(2022, 12, 7, 18, 30),
        encordador = getUsuariosInit()[2]
    ),
    Turno(
        id = 1,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 5, 16, 5),
        final = LocalDateTime.of(2022, 12, 6, 9, 0),
        encordador = getUsuariosInit()[4]
    ),
    Turno(
        id = 2,
        uuid = UUID.randomUUID(),
        comienzo = LocalDateTime.of(2022, 12, 1, 9, 0),
        final = LocalDateTime.of(2022, 12, 2, 20, 0),
        encordador = getUsuariosInit()[4]
    )
)

fun getProductosInit() = listOf(
    Producto(
        id = 0,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.COMPLEMENTO,
        marca = "ADIDAS",
        modelo = "33-A",
        precio = 15.5f,
        stock = 35,
        pedido = getPedidosInit()[0]
    ),
    Producto(
        id = 1,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.RAQUETA,
        marca = "DUNLOP",
        modelo = "XXL",
        precio = 20.0f,
        stock = 10,
        pedido = getPedidosInit()[0]
    ),
    Producto(
        id = 2,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.COMPLEMENTO,
        marca = "J'HAYBER",
        modelo = "Ultimate",
        precio = 9.99f,
        stock = 20,
        pedido = getPedidosInit()[1]
    ),
    Producto(
        id = 3,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.CORDAJE,
        marca = "JOMA",
        modelo = "5-7B",
        precio = 4.99f,
        stock = 100,
        pedido = getPedidosInit()[1]
    ),
    Producto(
        id = 4,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.CORDAJE,
        marca = "ADIDAS",
        modelo = "Sport",
        precio = 79.95f,
        stock = 18,
        pedido = getPedidosInit()[2]
    ),
    Producto(
        id = 5,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.RAQUETA,
        marca = "BULLPADEL",
        modelo = "2023",
        precio = 1200.0f,
        stock = 1,
        pedido = getPedidosInit()[2]
    ),
    Producto(
        id = 6,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.RAQUETA,
        marca = "DUNLOP",
        modelo = "Origin",
        precio = 12.0f,
        stock = 11,
        pedido = getPedidosInit()[3]
    ),
    Producto(
        id = 7,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.COMPLEMENTO,
        marca = "ASICS",
        modelo = "Retro",
        precio = 12.15f,
        stock = 89,
        pedido = getPedidosInit()[3]
    ),
    Producto(
        id = 8,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.CORDAJE,
        marca = "ASICS",
        modelo = "Seller",
        precio = 3.99f,
        stock = 96,
        pedido = getPedidosInit()[4]
    ),
    Producto(
        id = 9,
        uuid = UUID.randomUUID(),
        tipoProducto = TipoProducto.CORDAJE,
        marca = "BABOLAT",
        modelo = "2002",
        precio = 8.75f,
        stock = 90,
        pedido = getPedidosInit()[4]
    )
)

fun getPedidosInit() = listOf(
    Pedido(
        id = 0,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.EN_PROCESO,
        encordador = getUsuariosInit()[2],
        fechaTope = LocalDate.of(2022, 12, 15),
        fechaEntrada = LocalDate.of(2022, 11, 10),
        fechaProgramada = LocalDate.of(2022, 12, 10),
        fechaEntrega = LocalDate.of(2022, 12, 10),
        precio = 120.0f
    ),
    Pedido(
        id = 1,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.EN_PROCESO,
        encordador = getUsuariosInit()[4],
        fechaTope = LocalDate.of(2022, 12, 27),
        fechaEntrada = LocalDate.of(2022, 12, 1),
        fechaProgramada = LocalDate.of(2022, 12, 20),
        fechaEntrega = LocalDate.of(2022, 12, 20),
        precio = 70.95f
    ),
    Pedido(
        id = 2,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.RECIBIDO,
        encordador = getUsuariosInit()[2],
        fechaTope = LocalDate.of(2023, 1, 5),
        fechaEntrada = LocalDate.of(2022, 12, 7),
        fechaProgramada = LocalDate.of(2023, 1, 4),
        fechaEntrega = LocalDate.of(2022, 1, 4),
        precio = 57.0f
    ),
    Pedido(
        id = 3,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.TERMINADO,
        encordador = getUsuariosInit()[4],
        fechaTope = LocalDate.of(2022, 12, 1),
        fechaEntrada = LocalDate.of(2022, 11, 5),
        fechaProgramada = LocalDate.of(2022, 11, 28),
        fechaEntrega = LocalDate.of(2022, 11, 25),
        precio = 50.15f
    ),
    Pedido(
        id = 4,
        uuid = UUID.randomUUID(),
        estado = TipoEstado.TERMINADO,
        encordador = getUsuariosInit()[2],
        fechaTope = LocalDate.of(2022, 11, 14),
        fechaEntrada = LocalDate.of(2022, 9, 5),
        fechaProgramada = LocalDate.of(2022, 10, 23),
        fechaEntrega = LocalDate.of(2022, 10, 23),
        precio = 3590.72f
    )
)

fun getTareasEncordadoInit() = listOf(
    TareaEncordado(
        id = 0,
        uuid = UUID.randomUUID(),
        precio = 100.0f,
        pedido = getPedidosInit()[2],
        tensionHorizontal = 22.5f,
        cordajeHorizontal = "Luxilon",
        tensionVertical = 22.5f,
        cordajeVertical = "Luxilon",
        nudos = NumeroNudos.CUATRO
    ),
    TareaEncordado(
        id = 1,
        uuid = UUID.randomUUID(),
        precio = 150.0f,
        pedido = getPedidosInit()[4],
        tensionHorizontal = 26f,
        cordajeHorizontal = "Wilson",
        tensionVertical = 22.5f,
        cordajeVertical = "Wilson",
        nudos = NumeroNudos.CUATRO
    )
)

fun getTareasPersonalizacion() = listOf(
    TareaPersonalizacion(
        id = 0,
        uuid = UUID.randomUUID(),
        precio = 70.0f,
        pedido = getPedidosInit()[1],
        peso = 0.3f,
        balance = 320.0f,
        rigidez = 70.0f
    ),
    TareaPersonalizacion(
        id = 1,
        uuid = UUID.randomUUID(),
        precio = 49.99f,
        pedido = getPedidosInit()[3],
        peso = 0.24f,
        balance = 330.0f,
        rigidez = 72.0f
    )
)

fun getMaquinasEncordar() = listOf(
    MaquinaEncordar(
        id = 0,
        uuid = UUID.randomUUID(),
        marca = "Vevor",
        modelo = "2021",
        fechaAdquisicion = LocalDate.of(2021, 11, 19),
        numeroSerie = 15,
        turno = getTurnosInit()[0],
        tipo = TipoEncordaje.AUTOMATICA,
        tensionMaxima = 30.0f,
        tensionMinima = 20.0f
    ),
    MaquinaEncordar(
        id = 1,
        uuid = UUID.randomUUID(),
        marca = "Vevor",
        modelo = "2022",
        fechaAdquisicion = LocalDate.of(2022, 5, 28),
        numeroSerie = 12,
        turno = getTurnosInit()[1],
        tipo = TipoEncordaje.MANUAL,
        tensionMaxima = 30.0f,
        tensionMinima = 20.0f
    )
)

fun getMaquinasPersonalizar() = listOf(
    MaquinaPersonalizar(
        id = 0,
        uuid = UUID.randomUUID(),
        marca = "Gamma",
        modelo = "X2",
        fechaAdquisicion = LocalDate.of(2022, 11, 5),
        numeroSerie = 10,
        turno = getTurnosInit()[0],
        mideManiobrabilidad = false,
        balance = 400.0f,
        rigidez = 80.0f
    ),
    MaquinaPersonalizar(
        id = 1,
        uuid = UUID.randomUUID(),
        marca = "Indoostrial",
        modelo = "2600",
        fechaAdquisicion = LocalDate.of(2022, 7, 12),
        numeroSerie = 3,
        turno = getTurnosInit()[1],
        mideManiobrabilidad = true,
        balance = 400.0f,
        rigidez = 80.0f
    )
)