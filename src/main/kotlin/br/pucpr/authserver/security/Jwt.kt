package br.pucpr.authserver.security

import br.pucpr.authserver.users.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import io.jsonwebtoken.jackson.io.JacksonSerializer
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class Jwt {
    private val key: Key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256)

    fun generateToken(user: User): String {
        return Jwts.builder()
            .setId(user.id.toString())
            .setSubject(user.name)
            .claim("role", user.role) // Certifique-se de que `role` existe no objeto User
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 3600000)) // 1 hora
            .serializeToJsonWith(JacksonSerializer())
            .signWith(key)
            .compact()
    }

    fun parseToken(token: String): UserToken {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .deserializeJsonWith(JacksonDeserializer(mapOf("role" to String::class.java)))
            .build()
            .parseClaimsJws(token)
            .body

        return UserToken(
            id = claims.id?.toLong() ?: throw IllegalArgumentException("ID is missing in token"),
            name = claims.subject ?: throw IllegalArgumentException("Name is missing in token"),
            role = claims["role"] as? String ?: throw IllegalArgumentException("Role is missing in token")
        )
    }

    fun getToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else null
    }
}

data class UserToken(
    val id: Long,
    val name: String,
    val role: String
)
