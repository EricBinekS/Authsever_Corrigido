package br.pucpr.authserver.livros

import org.springframework.data.jpa.repository.JpaRepository

interface LivroRepository : JpaRepository<Livro, Long>
