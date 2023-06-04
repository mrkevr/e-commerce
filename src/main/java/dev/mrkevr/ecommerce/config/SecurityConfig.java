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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import dev.mrkevr.ecommerce.servioe.ApplicationUserManager;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
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
		http.csrf(c -> c.disable());
		
        http.authorizeHttpRequests(auth -> {
        	auth
        	.requestMatchers("/login/**","/register/**", "/js/**", "/css/**").permitAll()
        	.requestMatchers("/admin/**").hasRole("ADMIN")
        	.anyRequest().authenticated();
        });
        
        http.formLogin(c -> c.loginPage("/login").successHandler(successHandler).usernameParameter("usernameEmail"));
        http.oauth2Login(c -> c.loginPage("/login").successHandler(successHandler));
        http.logout(c -> c.logoutUrl("/logout").logoutSuccessUrl("/login"));
        
        return http.build();
    }
}
