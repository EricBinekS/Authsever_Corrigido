package br.pucpr.authserver.generos

import br.pucpr.authserver.livros.Livro
import jakarta.persistence.* //para suporte de anotações JPA

@Entity
data class GeneroLiterario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val nome: String,

    @OneToMany(mappedBy = "genero", cascade = [CascadeType.ALL], orphanRemoval = true) // relacionamento
    val livros: List<Livro> = mutableListOf()
)
