/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package services

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets


/**
 * Función que codifica una cadena de carcateres introducida usando sha512.
 *
 * @param password La cadena de carcateres a codificar.
 *
 * @return String, la cadena de caracteres introducida ya codificada.
 */fun passwordCodification(password: String): String {
    return Hashing.sha256()
        .hashString(password, StandardCharsets.UTF_8)
        .toString()
}