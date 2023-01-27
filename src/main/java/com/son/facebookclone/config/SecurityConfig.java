package com.son.facebookclone.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.son.facebookclone.filter.JwtAuthorizationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthorizationFilter jwtFilter;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.cors(Customizer.withDefaults());
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		http.authorizeHttpRequests(auth -> auth
						.antMatchers("/welcome", 
									"/api/v1/auth/login",
									"/api/v1/auth/register",
									"/api/v1/auth/posts/**",
									
									"/swagger-ui.html",
									"/v2/api-docs", 
									"/swagger-resources/configuration/ui", 
									"/swagger-resources", 
									"/swagger-resources/configuration/security", 
									"/swagger-ui.html", 
									"/webjars/**").permitAll()
						.anyRequest().authenticated());

		return http.build();
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS"));
        
        corsConfiguration.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
