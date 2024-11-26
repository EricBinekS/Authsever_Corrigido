package br.pucpr.authserver.generos

import br.pucpr.authserver.livros.Livro
import org.springframework.stereotype.Service
import jakarta.persistence.EntityNotFoundException

@Service
class GeneroLiterarioService(
    private val generoRepository: GeneroLiterarioRepository
) {
    fun listarLivrosPorGenero(id: Long): List<Livro> {
        val genero = generoRepository.findById(id).orElseThrow {
            EntityNotFoundException("Gênero com ID $id não encontrado")
        }
        return genero.livros
    }

    fun criarGenero(genero: GeneroLiterario): GeneroLiterario {
        return generoRepository.save(genero)
    }

    fun listarGeneros(): List<GeneroLiterario> {
        return generoRepository.findAll()
    }
}
