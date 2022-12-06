package db

import config.AppConfig
import entities.*
import entities.maquinas.MaquinasEncordarTable
import entities.maquinas.MaquinasPersonalizarTable
import entities.tareas.TareasEncordadoTable
import entities.tareas.TareasPersonalizacionTable
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
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
}