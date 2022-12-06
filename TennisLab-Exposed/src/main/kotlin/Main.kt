import config.AppConfig
import db.DataBaseManager
import db.getUsuariosInit
import entities.UsuariosDAO
import repositories.usuario.UsuariosRepository

fun main(args: Array<String>) {
    initDataBase()
    val repo = UsuariosRepository(UsuariosDAO)

    getUsuariosInit().forEach { usuario ->
        repo.save(usuario)
    }

    repo.findAll()
}

fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    DataBaseManager.init(appConfig)

}