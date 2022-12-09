package db

import config.AppConfig
import entities.*
import entities.maquinas.MaquinasEncordarTable
import entities.maquinas.MaquinasPersonalizarTable
import entities.tareas.TareasEncordadoTable
import entities.tareas.TareasPersonalizacionTable
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

private val logger = KotlinLogging.logger {}

object DataBaseManager {
    lateinit var appConfig: AppConfig
    fun init(appConfig: AppConfig) {
        this.appConfig = appConfig
        logger.debug("Initializing database")
        Database.connect(
            url = appConfig.jdbcUrl,
            driver = appConfig.jdbcDriverClassName,
            user = appConfig.jdbcUserName,
            password = appConfig.jdbcPassword
        )

        logger.debug("Database initialized successfully")

        if (appConfig.jdbcCreateTables) {
            createTables()
        }
    }

    private fun createTables() = transaction {
        logger.debug("Creating tables")

        if (appConfig.jdbcshowSQL)
            addLogger(StdOutSqlLogger)

        SchemaUtils.create(
            MaquinasEncordarTable,
            MaquinasPersonalizarTable,
            PedidosTable,
            ProductosTable,
            TareasEncordadoTable,
            TareasPersonalizacionTable,
            TurnosTable,
            UsuariosTable
        )
        logger.debug("Tables created")
    }

    fun dropTables() = transaction {
        logger.debug { "Eliminando tablas de la base de datos" }

        if (appConfig.jdbcshowSQL)
            addLogger(StdOutSqlLogger) // Para que se vea el log de consulas a la base de datos

        // Mis tablas
        val tables = arrayOf(
            MaquinasEncordarTable,
            MaquinasPersonalizarTable,
            PedidosTable,
            ProductosTable,
            TareasEncordadoTable,
            TareasPersonalizacionTable,
            TurnosTable,
            UsuariosTable
        )

        SchemaUtils.drop(*tables)
        logger.debug { "Tablas eliminadas (${tables.size}): ${tables.joinToString { it.tableName }}" }
    }

    fun clearTables() = transaction {
        logger.debug { "Limpiando tablas de la base de datos" }

        if (appConfig.jdbcshowSQL)
            addLogger(StdOutSqlLogger) // Para que se vea el log de consulas a la base de datos

        // Mis tablas
        val tables = arrayOf(
            MaquinasEncordarTable,
            MaquinasPersonalizarTable,
            PedidosTable,
            ProductosTable,
            TareasEncordadoTable,
            TareasPersonalizacionTable,
            TurnosTable,
            UsuariosTable
        )

        tables.forEach {
            it.deleteAll()
        }
        logger.debug { "Tablas limpiadas (${tables.size}): ${tables.joinToString { it.tableName }}" }
    }
}