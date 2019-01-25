package com.gestion.rh.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gestion.rh.models.ApplicationContextProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ApplicationContextProvider provider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		System.out.println(" inside configure function !!!!!! ");
	}
	
	protected void configure(HttpSecurity http) throws Exception{	
		http			
			.cors().configurationSource( (CorsConfigurationSource) provider.getApplicationContext().getBean("corsConfigurationSource", CorsConfigurationSource.class) )
		.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_USER_URL).permitAll()
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_EMPLOYEE_URL).permitAll()
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_CANDIDATE_URL).permitAll()
			.antMatchers(HttpMethod.GET, "/rh/photo/{name}").permitAll()
			.antMatchers(HttpMethod.GET, "/candidate/photo/{name}").permitAll()
			.antMatchers(HttpMethod.GET, "/offre/image/{name}").permitAll()
			.antMatchers(HttpMethod.GET, "/offre/**").permitAll()
			.antMatchers(HttpMethod.POST, "/offre/search").permitAll()
			.antMatchers("/rh/**").hasAuthority("EMPLOYEE_ROLE")
			.antMatchers(HttpMethod.POST, "/offre/apply").hasAuthority("CANDIDATE_ROLE")
			.antMatchers("/offre/**").hasAuthority("EMPLOYEE_ROLE")
			.antMatchers(HttpMethod.POST, "/candidate/convocate").hasAuthority("EMPLOYEE_ROLE")
			.antMatchers(HttpMethod.GET, "/candidate/all").hasAuthority("EMPLOYEE_ROLE")
			.antMatchers(HttpMethod.POST, "/candidate/convocate/quizz/{id}").hasAuthority("EMPLOYEE_ROLE")
			.antMatchers("/candidate/**").hasAuthority("CANDIDATE_ROLE")
        .and()
	        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
	        .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService((UserDetailsService) provider.getApplicationContext().getBean("userdetailservice", UserDetailsService.class));
	    authProvider.setPasswordEncoder((BCryptPasswordEncoder) provider.getApplicationContext().getBean("bcryptPasswordencoder", BCryptPasswordEncoder.class));
	    return authProvider;
	}
	
}
