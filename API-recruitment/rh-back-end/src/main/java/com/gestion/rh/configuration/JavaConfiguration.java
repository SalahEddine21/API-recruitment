package com.gestion.rh.configuration;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gestion.rh.candidate.cv.Cv;
import com.gestion.rh.models.ApplicationContextProvider;
import com.gestion.rh.quiz.Part;
import com.gestion.rh.quiz.Question;
import com.gestion.rh.quiz.Quiz;
import com.gestion.rh.roles.Role;
import com.gestion.rh.security.UserDetailsServiceImpl;
import com.gestion.rh.simpleToken.SimpleToken;


@Configuration
public class JavaConfiguration {

	@Bean(name="provider")
	public ApplicationContextProvider getApplicationContext_Bean() {
		return new ApplicationContextProvider();
	}
	
	@Bean
	@Scope("prototype")
	public SimpleGrantedAuthority simpleGrantedauthority(String role) {
		return new SimpleGrantedAuthority(role);
	}
	
	@Bean
	@Scope("prototype")
	public BCryptPasswordEncoder bcryptPasswordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PUT"));
		configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers","Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean 
	public UserDetailsService userdetailservice() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	@Scope("prototype")
	public Role role() {
		return new Role();
	}
	
	@Bean
	@Scope("prototype")
	public File file(String path) {
		return new File(path);
	}
	
	@Bean
	@Scope("prototype")
	public BufferedInputStream bufferedInputStream(FileInputStream fis) {
		return new BufferedInputStream(fis);
	}
	
	@Bean
	@Scope("prototype")
	public FileInputStream fileInputStream(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}
	
	@Bean
	@Scope("prototype")
	public BufferedReader bufferedReader(FileReader fr) {
		return new BufferedReader(fr);
	}
	
	@Bean
	@Scope("prototype")
	public FileReader fileReader(String filename) throws FileNotFoundException {
		return new FileReader(filename);
	}

	@Bean
	@Scope("prototype")
	public List<String> getStringList(){
		return new ArrayList<String>();
	}
	
	@Bean
	@Scope("prototype")
	public Date utilDate() {
		return new Date();
	}
	
	@Bean
	@Scope("prototype")
	public java.sql.Date sqlDate(long time){
		return new java.sql.Date(time);
	}
	
	@Bean
	@Scope("prototype")
	public SimpleDateFormat simpleDateformat(String format) {
		return new SimpleDateFormat(format);
	}
	
	@Bean
	@Scope("prototype")
	public SimpleMailMessage simpleMailmessage() {
		return new SimpleMailMessage();
	}
	
	@Bean
	@Scope("prototype")
	public Cv cv() {
		return new Cv();
	}
	
	@Bean
	@Scope("prototype")
	public Question question() {
		return new Question();
	}

	@Bean
	@Scope("prototype")
	public Part part() {
		return new Part();
	}
	
	@Bean
	@Scope("prototype")
	public Quiz quiz() {
		return new Quiz();
	}

	@Bean
	@Scope("prototype")
	public SimpleToken simpleToken(String tokenValue, LocalDate startDate, int eXPIRATION_TIME) {
		return new SimpleToken(tokenValue, startDate, eXPIRATION_TIME);
	}
}
