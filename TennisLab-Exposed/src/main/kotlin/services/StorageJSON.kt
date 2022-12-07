package services

import dto.PedidoDTO
import dto.ProductoDTO
import dto.TurnoDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

class StorageJSON {
    fun writePedido(nombreArchivo: String, entityDTO: List<PedidoDTO>) {
        logger.info("Escribiendo JSON.")
        val directorio = System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "resources"
        val fichero = File(directorio + File.separator + "$nombreArchivo.json")
        val json = Json { prettyPrint = true }
        fichero.writeText(json.encodeToString(entityDTO))
    }

    fun writeProducto(nombreArchivo: String, entityDTO: List<ProductoDTO>) {
        logger.info("Escribiendo JSON.")
        val directorio = System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "resources"
        val fichero = File(directorio + File.separator + "$nombreArchivo.json")
        val json = Json { prettyPrint = true }
        fichero.writeText(json.encodeToString(entityDTO))
    }

    fun writeTurno(nombreArchivo: String, entityDTO: List<TurnoDTO>) {
        logger.info("Escribiendo JSON.")
        val directorio = System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "resources"
        val fichero = File(directorio + File.separator + "$nombreArchivo.json")
        val json = Json { prettyPrint = true }
        fichero.writeText(json.encodeToString(entityDTO))
    }

}