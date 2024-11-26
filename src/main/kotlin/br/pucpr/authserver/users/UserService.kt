package br.pucpr.authserver.users

import br.pucpr.authserver.roles.Role
import br.pucpr.authserver.security.Jwt
import org.springframework.stereotype.Service

@Service
class UserService(private val jwt: Jwt) {
    fun login(email: String, password: String): String {
        val user = findUserByEmailAndPassword(email, password)
        return jwt.createToken(user)
    }

    private fun findUserByEmailAndPassword(email: String, password: String): User {

        return User(
            id = 1L,
            name = "John Doe",
            email = email,
            password = password,
            roles = mutableSetOf(Role(
                name = "ADMIN",
                id = TODO(),
                description = TODO()
            ))
        )
    }
}
