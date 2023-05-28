package dev.mrkevr.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private ApplicationUserManager userManager;
	private AuthenticationSuccessHandler successHandler;
	
	// Use constructor injection with @Lazy to avoid circular dependency
	public SecurityConfig(
			@Lazy ApplicationUserManager userManager, 
			@Lazy AuthenticationSuccessHandler successHandler) {
		
		super();
		this.userManager = userManager;
		this.successHandler = successHandler;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userManager);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
	
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {    
		http.csrf().disable()
        .authorizeHttpRequests(auth -> {
        	auth
        		.requestMatchers("/login/**","/register/**", "/js/**", "/css/**").permitAll()
        		.requestMatchers("/admin/**").hasRole("ADMIN")
        		.anyRequest().authenticated();
        })
        .formLogin().loginPage("/login").successHandler(successHandler).usernameParameter("usernameEmail")
        .and().csrf().disable()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
        .and().oauth2Login().loginPage("/login").successHandler(successHandler);
		
        return http.build();
    }
}
