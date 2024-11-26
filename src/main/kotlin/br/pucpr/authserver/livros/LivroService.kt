package br.pucpr.authserver.livros

import org.springframework.stereotype.Service
import jakarta.persistence.EntityNotFoundException

@Service
class LivroService(
    private val livroRepository: LivroRepository
) {
    fun criarLivro(request: LivroRequest): Livro {
        val livro = Livro(
            titulo = request.titulo,
            autor = request.autor,
            anoPublicacao = request.anoPublicacao,
            disponivel = request.disponivel
        )
        return livroRepository.save(livro)
    }

    fun listarLivros(): List<Livro> {
        return livroRepository.findAll()
    }

    fun buscarLivroPorId(id: Long): Livro {
        return livroRepository.findById(id).orElseThrow {
            EntityNotFoundException("Livro com ID $id não encontrado")
        }
    }

    fun atualizarLivro(id: Long, request: LivroRequest): Livro {
        val livro = buscarLivroPorId(id)
        livro.titulo = request.titulo
        livro.autor = request.autor
        livro.anoPublicacao = request.anoPublicacao
        livro.disponivel = request.disponivel
        return livroRepository.save(livro)
    }

    fun deletarLivro(id: Long) {
        if (!livroRepository.existsById(id)) {
            throw EntityNotFoundException("Livro com ID $id não encontrado")
        }
        livroRepository.deleteById(id)
    }

    fun pesquisarLivros(
        titulo: String?,
        autor: String?,
        disponivel: Boolean?,
        ordem: String?
    ): List<Livro> {
        val livros = livroRepository.findAll()

        // Aplicar filtros opcionais
        val filtrados = livros.filter {
            (titulo == null || it.titulo.contains(titulo, ignoreCase = true)) &&
                    (autor == null || it.autor.contains(autor, ignoreCase = true)) &&
                    (disponivel == null || it.disponivel == disponivel)
        }

        // Aplicar ordenação
        return when (ordem?.lowercase()) {
            "asc" -> filtrados.sortedBy { it.anoPublicacao }
            "desc" -> filtrados.sortedByDescending { it.anoPublicacao }
            else -> filtrados
        }
    }
}
