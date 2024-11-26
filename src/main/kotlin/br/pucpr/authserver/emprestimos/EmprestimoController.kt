package br.pucpr.authserver.emprestimos

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import br.pucpr.authserver.emprestimos.EmprestimoService

@RestController
@RequestMapping("/api/emprestimos")
class EmprestimoController(
    private val emprestimoService: EmprestimoService
) {
    @PostMapping
    fun criarEmprestimo(
        @RequestParam usuarioId: Long,
        @RequestParam livroId: Long
    ): ResponseEntity<Emprestimo> {
        val emprestimo = emprestimoService.criarEmprestimo(usuarioId, livroId)
        return ResponseEntity.ok(emprestimo)
    }

    @GetMapping
    fun listarEmprestimos(): List<Emprestimo> {
        return emprestimoService.listarEmprestimos()
    }

    @PatchMapping("/{id}/devolver")
    fun devolverEmprestimo(@PathVariable id: Long): ResponseEntity<Emprestimo> {
        val emprestimo = emprestimoService.devolverEmprestimo(id)
        return ResponseEntity.ok(emprestimo)
    }
}
