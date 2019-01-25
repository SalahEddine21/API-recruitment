package com.gestion.rh.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter  extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		res.addHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Headers,authorization");
		res.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Access-Control-Allow-Credentiels,authorization");
		System.out.println("doFilterInternal");
		String header = req.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
        	filterChain.doFilter(req, res);
            return;
        }
        
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(req, res);
		
	}
	
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
    	String header = req.getHeader(SecurityConstants.HEADER_STRING);
    	
		Claims claims=(Claims) Jwts.parser()
				.setSigningKey(SecurityConstants.SECRET)
				.parse(header.replace(SecurityConstants.TOKEN_PREFIX, ""))
				.getBody();
		
		String username=claims.getSubject();
		if(username != null){
			ArrayList<Map<String, String>> roles=(ArrayList<Map<String,String>>)claims.get("roles");
			Collection<GrantedAuthority> authorities=new ArrayList<>();
			roles.forEach(r->{
				authorities.add(new SimpleGrantedAuthority(r.get("authority")));	
			});
			
			roles.forEach(r -> {
				System.out.println(r.get("authority"));
			});
			
			return 	new UsernamePasswordAuthenticationToken(username,null, authorities);
		}
        return null;
    }

}
