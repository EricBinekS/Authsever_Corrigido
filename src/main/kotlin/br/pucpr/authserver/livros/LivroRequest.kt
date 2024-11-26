package br.pucpr.authserver.livros

data class LivroRequest(
    val titulo: String,
    val autor: String,
    val anoPublicacao: Int,
    val disponivel: Boolean
)
