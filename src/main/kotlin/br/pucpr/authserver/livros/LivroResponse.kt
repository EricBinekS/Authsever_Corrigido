package br.pucpr.authserver.livros

data class LivroResponse(
    val id: Long,
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val disponivel: Boolean
)
