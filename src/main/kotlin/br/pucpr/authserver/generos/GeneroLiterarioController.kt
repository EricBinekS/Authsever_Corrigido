package br.pucpr.authserver.generos

import br.pucpr.authserver.livros.Livro
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/generos")
class GeneroLiterarioController(
    private val generoService: GeneroLiterarioService
) {
    @PostMapping
    fun criarGenero(@RequestBody genero: GeneroLiterario): ResponseEntity<GeneroLiterario> {
        val generoSalvo = generoService.criarGenero(genero)
        return ResponseEntity.status(HttpStatus.CREATED).body(generoSalvo)
    }

    @GetMapping
    fun listarGeneros(): ResponseEntity<List<GeneroLiterario>> {
        val generos = generoService.listarGeneros()
        return ResponseEntity.ok(generos)
    }

    @GetMapping("/{id}/livros")
    fun listarLivrosPorGenero(@PathVariable id: Long): ResponseEntity<List<Livro>> {
        val livros = generoService.listarLivrosPorGenero(id)
        return ResponseEntity.ok(livros)
    }
}
