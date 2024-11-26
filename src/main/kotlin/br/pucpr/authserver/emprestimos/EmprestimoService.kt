package br.pucpr.authserver.emprestimos

import org.springframework.stereotype.Service
import br.pucpr.authserver.livros.LivroRepository
import br.pucpr.authserver.users.UserRepository
import jakarta.persistence.EntityNotFoundException
import java.time.LocalDate

@Service
class EmprestimoService(
    private val emprestimoRepository: EmprestimoRepository,
    private val livroRepository: LivroRepository,
    private val userRepository: UserRepository
) {
    fun criarEmprestimo(usuarioId: Long, livroId: Long): Emprestimo {
        val usuario = userRepository.findById(usuarioId).orElseThrow {
            EntityNotFoundException("Usuário não encontrado")
        }
        val livro = livroRepository.findById(livroId).orElseThrow {
            EntityNotFoundException("Livro não encontrado")
        }
        if (!livro.disponivel) {
            throw IllegalStateException("Livro não está disponível para empréstimo")
        }

        livro.disponivel = false
        val emprestimo = Emprestimo(usuario = usuario, livro = livro)
        return emprestimoRepository.save(emprestimo)
    }

    fun listarEmprestimos(): List<Emprestimo> {
        return emprestimoRepository.findAll()
    }

    fun devolverEmprestimo(id: Long): Emprestimo {
        val emprestimo = emprestimoRepository.findById(id).orElseThrow {
            EntityNotFoundException("Empréstimo não encontrado")
        }
        emprestimo.livro.disponivel = true // Atualização permitida
        emprestimo.dataDevolucao = LocalDate.now() // Atualização permitida
        emprestimo.status = "DEVOLVIDO" // Atualização permitida
        return emprestimoRepository.save(emprestimo)
    }
}
