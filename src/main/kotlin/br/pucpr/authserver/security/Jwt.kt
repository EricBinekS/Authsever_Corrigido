package br.pucpr.authserver.security

import br.pucpr.authserver.users.User
import io.jsonwebtoken.Jwts
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import io.jsonwebtoken.jackson.io.JacksonSerializer
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
<<<<<<< Updated upstream
=======
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
>>>>>>> Stashed changes
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class Jwt {
    private val key: Key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256)

<<<<<<< Updated upstream
    fun generateToken(user: User): String {
        return Jwts.builder()
            .setId(user.id.toString())
            .setSubject(user.name)
            .claim("role", user.role) // Certifique-se de que `role` existe no objeto User
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 3600000)) // 1 hora
=======
    fun createToken(user: User): String {
        val roles = user.roles.map { it.name }
        return Jwts.builder()
            .setId(user.id.toString())
            .setSubject(user.name)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 3600000))
>>>>>>> Stashed changes
            .serializeToJsonWith(JacksonSerializer())
            .signWith(key)
            .compact()
    }

    fun parseToken(token: String): UserToken {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
<<<<<<< Updated upstream
            .deserializeJsonWith(JacksonDeserializer(mapOf("role" to String::class.java)))
=======
            .deserializeJsonWith(JacksonDeserializer(mapOf("roles" to List::class.java)))
>>>>>>> Stashed changes
            .build()
            .parseClaimsJws(token)
            .body

<<<<<<< Updated upstream
        return UserToken(
            id = claims.id?.toLong() ?: throw IllegalArgumentException("ID is missing in token"),
            name = claims.subject ?: throw IllegalArgumentException("Name is missing in token"),
            role = claims["role"] as? String ?: throw IllegalArgumentException("Role is missing in token")
=======
        val roles = claims["roles"] as? List<*>
            ?: throw IllegalArgumentException("Roles are missing in token")

        return UserToken(
            id = claims.id?.toLong() ?: throw IllegalArgumentException("ID is missing in token"),
            name = claims.subject ?: throw IllegalArgumentException("Name is missing in token"),
            roles = roles.filterIsInstance<String>()
>>>>>>> Stashed changes
        )
    }

    fun getToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else null
    }
}

<<<<<<< Updated upstream
data class UserToken(
    val id: Long,
    val name: String,
    val role: String
)
=======
    fun extract(request: HttpServletRequest): Authentication? {
        val token = getToken(request) ?: return null
        return try {
            val userToken = parseToken(token)
            UsernamePasswordAuthenticationToken(
                userToken,
                null,
                userToken.roles.map { SimpleGrantedAuthority(it) }
            )
        } catch (ex: Exception) {
            null
        }
    }



}
>>>>>>> Stashed changes
