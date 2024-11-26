package br.pucpr.authserver.emprestimos

import org.springframework.data.jpa.repository.JpaRepository

interface EmprestimoRepository : JpaRepository<Emprestimo, Long>
