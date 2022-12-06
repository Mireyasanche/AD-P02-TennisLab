package services

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

class StorageJSON<T> {
    fun write(nombreArchivo: String, entityDTO: List<T>) {
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