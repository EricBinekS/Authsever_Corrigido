package br.pucpr.authserver.security

import br.pucpr.authserver.users.User
import com.fasterxml.jackson.annotation.JsonIgnore

data class UserToken(
    val id: Long,
    val name: String,
    val roles: List<String>
) {
    constructor(): this(0, "", listOf())
    constructor(user: User) : this(
        id = user.id ?: -1L,
        name = user.name,
        roles = user.roles.map { it.name }.sorted()
    )

    @get:JsonIgnore
    val isAdmin: Boolean get() = "ADMIN" in roles
}
