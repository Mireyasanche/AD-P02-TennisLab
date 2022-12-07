package services

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

fun passwordCodification(password: String): String {
    return Hashing.sha256()
        .hashString(password, StandardCharsets.UTF_8)
        .toString()
}