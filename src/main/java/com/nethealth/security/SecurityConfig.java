package com.nethealth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	

	@Autowired
	UserDetailsService userDetailService;
	
	@Bean
	public BCryptPasswordEncoder bcrypPasswordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	
	@Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	registry.addMapping("**");
            }
        };
    }
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(bcrypPasswordEncoder());	
	}
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/v2/api-docs",
	                "/configuration/ui",
	                "/swagger-resources/**",
	                "/configuration/security",
	                "/swagger-ui/**",
	                "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/doctors/account/confirm").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/patients/account/confirm" ).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/doctors").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/patients").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users/login").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/password/reset").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/password").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		
	}
	
	
	
	
	
	

}
