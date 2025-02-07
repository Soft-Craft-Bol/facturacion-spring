package com.gaspar.facturador.config;


import com.gaspar.facturador.config.filter.JwtTokenValidator;
import com.gaspar.facturador.domain.service.UserDetailServiceImpl;
import com.gaspar.facturador.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Origen permitido (tu frontend)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Headers permitidos
        configuration.setAllowCredentials(true); // Permitir credenciales (necesario para cookies, headers de autorización, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar a todas las rutas
        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .csrf().disable() // Deshabilitar CSRF (no es necesario para APIs stateless)
                .cors().configurationSource(corsConfigurationSource()) // Aplicar configuración de CORS
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sin sesiones
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/codigos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/factura/**").permitAll()
                .antMatchers(HttpMethod.GET, "/clientes/**").permitAll()
                .antMatchers(HttpMethod.POST, "/clientes/**").permitAll()
                .antMatchers(HttpMethod.GET, "/productos-servicios/**").permitAll()
                .antMatchers(HttpMethod.POST, "/factura/emitir").permitAll()
                .antMatchers(HttpMethod.GET, "/items").permitAll()
                .antMatchers(HttpMethod.GET, "/puntos-venta").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()// Permitir acceso público a /auth/**
                .antMatchers(HttpMethod.GET, "/method/get").hasAuthority("READ")
                .antMatchers(HttpMethod.POST, "/method/post").hasAuthority("CREATE")
                .antMatchers(HttpMethod.DELETE, "/method/delete").hasAuthority("DELETE")
                .antMatchers(HttpMethod.PUT, "/method/put").hasAuthority("UPDATE")
                .anyRequest().denyAll() // Denegar cualquier otra solicitud
                .and()
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
