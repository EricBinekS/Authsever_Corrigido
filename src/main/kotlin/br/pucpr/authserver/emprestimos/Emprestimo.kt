package br.pucpr.authserver.emprestimos;

import jakarta.persistence.* //para suporte de anotações JPA
import java.time.LocalDate  //manipulação de datas em datEmprestimo e dataDevolução
import br.pucpr.authserver.users.User
import br.pucpr.authserver.livros.Livro

@Entity
data class Emprestimo(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "usuario_id", nullable = false)
        val usuario: User,

        @ManyToOne
        @JoinColumn(name = "livro_id", nullable = false)
        val livro: Livro,

        @Column(nullable = false)
        val dataEmprestimo: LocalDate = LocalDate.now(),

        @Column
        var dataDevolucao: LocalDate? = null,

        @Column(nullable = false)
        var status: String = "EMPRESTADO"
)
