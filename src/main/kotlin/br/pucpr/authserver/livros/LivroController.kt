package br.pucpr.authserver.livros

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("/api/livros")
class LivroController(
    private val livroService: LivroService
) {
    // Criar um novo livro
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Apenas administradores podem criar livros
    fun criarLivro(@RequestBody request: LivroRequest): ResponseEntity<LivroResponse> {
        val livro = livroService.criarLivro(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(livro.toResponse())
    }

    // Listar todos os livros com filtros e ordenação
    @GetMapping
    fun listarLivrosComFiltros(
        @RequestParam(required = false) titulo: String?,
        @RequestParam(required = false) autor: String?,
        @RequestParam(required = false) disponivel: Boolean?,
        @RequestParam(required = false) ordem: String?
    ): ResponseEntity<List<LivroResponse>> {
        val livros = livroService.pesquisarLivros(titulo, autor, disponivel, ordem)
        return ResponseEntity.ok(livros.map { it.toResponse() })
    }

    // Buscar um livro por ID
    @GetMapping("/{id}")
    fun buscarLivroPorId(@PathVariable id: Long): ResponseEntity<LivroResponse> {
        val livro = livroService.buscarLivroPorId(id)
        return ResponseEntity.ok(livro.toResponse())
    }

    // Atualizar um livro existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')") // Admins ou editores podem atualizar livros
    fun atualizarLivro(
        @PathVariable id: Long,
        @RequestBody request: LivroRequest
    ): ResponseEntity<LivroResponse> {
        val livroAtualizado = livroService.atualizarLivro(id, request)
        return ResponseEntity.ok(livroAtualizado.toResponse())
    }

    // Deletar um livro por ID
    
    @DeleteMapping("/{id}")
    // Logging for deletion of books
    fun logDeletion(id: Long) {
        println("LivroController: Attempting to delete book with ID $id")
    }
    
    @PreAuthorize("hasRole('ADMIN')") // Apenas administradores podem deletar livros
    fun deletarLivro(@PathVariable id: Long): ResponseEntity<Void> {
        livroService.deletarLivro(id)
        return ResponseEntity.noContent().build()
    }
}
