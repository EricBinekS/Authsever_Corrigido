package br.pucpr.authserver.livros

import br.pucpr.authserver.generos.GeneroLiterario
import jakarta.persistence.*

@Entity
data class Livro(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var titulo: String,

    @Column(nullable = false)
    var autor: String,

    @Column(nullable = false)
    var anoPublicacao: Int,

    @Column(nullable = false)
    var disponivel: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "genero_id")
    var genero: GeneroLiterario? = null
)

fun Livro.toResponse(): LivroResponse {
    return LivroResponse(
        id = this.id!!,
        titulo = this.titulo,
        autor = this.autor,
        anoPublicacao = this.anoPublicacao,
        disponivel = this.disponivel
    )
}


