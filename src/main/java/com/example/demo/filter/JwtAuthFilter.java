package com.example.demo.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.JwtService;
import com.example.demo.service.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

// Cette classe sert à valider le jwt généré

@Log
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	@Lazy
	private UserInfoService userDetailsService; 

	 @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	        try {
	            String authHeader = request.getHeader("Authorization");
	            String token = null;
	            String email = null;

	            if (authHeader != null && authHeader.startsWith("Bearer ")) {
	                token = authHeader.substring(7);
	                email = jwtService.extractUsername(token);
	            }

	            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
	                if (jwtService.validateToken(token, userDetails)) {
	                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                    SecurityContextHolder.getContext().setAuthentication(authToken);
	                }
	            }
	        } catch (Exception ex) {
	            // Handle the exception:
	            // - Log the exception
	            // - Optionally, modify the response
	            // - Optionally, return an error response

	            log.severe("An error occurred in JwtAuthFilter: " + ex.getMessage());

	            // throw new ServletException(ex);
	            
	        }
	        filterChain.doFilter(request, response);
	    }
}
