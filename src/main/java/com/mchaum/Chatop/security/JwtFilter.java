package com.mchaum.Chatop.security;

import com.mchaum.Chatop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	
	private final UserService userService;
	private final JwtUtils jwtUtils;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		 String requestPath = request.getRequestURI();

		    // Exclure Swagger UI et autres endpoints publics (login +register+error) //
		    if (requestPath.startsWith("/swagger-ui") || 
		        requestPath.startsWith("/v3/api-docs") || 
		        requestPath.equals("/error") || 
		        requestPath.equals("/auth/login") || 
		        requestPath.equals("/auth/register")) {
		        filterChain.doFilter(request, response);
		        return;
		    }
		
		final String authHeader = request.getHeader("Authorization");
		
		String email = null;
		String jwt = null;
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7); // 7 caractères Bearer + espace //
			email = jwtUtils.extractEmail(jwt);
		}
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// On vérifie que l'utilisateur n'est pas connecté //
			UserDetails userDetails = userService.loadUserByUsername(email);
			
			if (jwtUtils.validatetoken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
}