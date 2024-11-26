package br.pucpr.authserver.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JwtTokenFilter(private val jwt: Jwt) : GenericFilterBean() {
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val httpRequest = req as HttpServletRequest
        val httpResponse = res as HttpServletResponse

        
    try {
        // Log the start of token extraction
        println("JwtTokenFilter: Starting token validation.")
    
            val auth = jwt.extract(httpRequest) // Extrai autenticação do token
            if (auth != null) {
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (e: Exception) {
            // Caso o token seja inválido ou ausente, limpar o contexto
            
        // Log invalid token
        println("JwtTokenFilter: Invalid or missing token.")
        SecurityContextHolder.clearContext()
    
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpResponse.writer.write("Token inválido ou ausente.")
            return
        }

        
        // Log successful token processing
        println("JwtTokenFilter: Token validated successfully.")
        chain.doFilter(req, res)
    
    }
}
