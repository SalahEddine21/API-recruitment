package com.gestion.rh.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}
	
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
        	System.out.println("attemptAuthentication");
        	com.gestion.rh.user.User creds = new ObjectMapper().readValue(req.getInputStream(), com.gestion.rh.user.User.class);
            System.out.println("-----------------------------------");
            System.out.println("username is: "+creds.getUsername());
            System.out.println("password is: "+creds.getPassword());
            System.out.println("-----------------------------------");
            
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

    	System.out.println("successfulAuthentication");
    	org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal(); 
    	
		String token = Jwts.builder()
       		 .setSubject(springUser.getUsername())
       		 .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
       		 .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
       		 .claim("roles", springUser.getAuthorities())
       		 .compact();
		
		String user = null;
        for (GrantedAuthority authority : springUser.getAuthorities()) {
        	if(authority.getAuthority().compareTo("CANDIDATE_ROLE") == 0) user = "candidate";
        	else if(authority.getAuthority().compareTo("EMPLOYEE_ROLE") == 0) user = "rh";
		}
        res.getWriter().write("{" + " \"user\" :  \""+user+"\" }");
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }

}
