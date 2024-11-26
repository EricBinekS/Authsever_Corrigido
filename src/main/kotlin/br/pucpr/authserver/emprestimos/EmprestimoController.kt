package br.pucpr.authserver.emprestimos

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import br.pucpr.authserver.emprestimos.EmprestimoService

@RestController
@RequestMapping("/api/emprestimos")
class EmprestimoController(
    private val emprestimoService: EmprestimoService
) {
    
    @PostMapping
    // Logging for creation of loans
    fun logCreation(usuarioId: Long, livroId: Long) {
        println("EmprestimoController: Creating loan for user $usuarioId and book $livroId")
    }
    
    @PreAuthorize("isAuthenticated()") // Apenas usuários autenticados podem criar empréstimos
    fun criarEmprestimo(
        @RequestParam usuarioId: Long,
        @RequestParam livroId: Long
    ): ResponseEntity<Emprestimo> {
        val emprestimo = emprestimoService.criarEmprestimo(usuarioId, livroId)
        return ResponseEntity.ok(emprestimo)
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')") // Somente administradores ou bibliotecários podem listar todos os empréstimos
    fun listarEmprestimos(): List<Emprestimo> {
        return emprestimoService.listarEmprestimos()
    }

    @PatchMapping("/{id}/devolver")
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode devolver um empréstimo
    fun devolverEmprestimo(@PathVariable id: Long): ResponseEntity<Emprestimo> {
        val emprestimo = emprestimoService.devolverEmprestimo(id)
        return ResponseEntity.ok(emprestimo)
    }
}
