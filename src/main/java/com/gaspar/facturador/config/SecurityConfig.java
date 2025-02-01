package com.gaspar.facturador.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                //Se encarga de validar los usuarios que si existen y de solo dar permiso a ciertos endpoins
//                .csrf(csrf -> csrf.disable())//.csrf(csrf -> csrf.disable()):
//                                            //La función flecha toma como argumento un objeto csrf y llama a su método .disable() para desactivar la protección contra ataques CSRF (más sobre esto abajo).
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(http -> {
//
//                    //cONFIGURACION DE LOS ENDPOINTS PUBLICOS
//                    http.requestMatchers(HttpMethod.GET, "/test/test").permitAll();
//                    //CONFIGURAR LOS ENDPOINTS PRIVADOS
//                    http.requestMatchers(HttpMethod.GET, "/test/test1").hasAuthority("CREATE");
//                    //CONIGURAR LOS ENDPOINTS NO ESPECIFICADOS
//                    http.anyRequest().denyall();
//                })
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //Se encarga de validar los usuarios que si existen y de solo dar permiso a ciertos endpoins
                .csrf(csrf -> csrf.disable())//.csrf(csrf -> csrf.disable()):
                //La función flecha toma como argumento un objeto csrf y llama a su método .disable() para desactivar la protección contra ataques CSRF (más sobre esto abajo).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguratio) throws Exception {
        return authenticationConfiguratio.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails userDetails = User.withUsername("gaspar")
//                .password("armando1gaspar")
//                .roles("ADMIN")
//                .authorities("READ", "CREATE")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

    //Tambien se puede retornar una lista de usuarios
    @Bean
    public UserDetailsService userDetailsService () {
        List<UserDetails> userDetailsList = new ArrayList<>();

        userDetailsList.add(User.withUsername("gaspar")
                .password("armando1gaspar")
                .roles("ADMIN")
                .authorities("READ", "CREATE")
                .build());
        userDetailsList.add(User.withUsername("Alfredo")
                .password("alfredo1gaspar")
                .roles("USER")
                .authorities("READ")
                .build());
        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); //Solo usar en modo de pruebas porque no encripta la contraseña
        //return new BCryptPasswordEncoder();
    }
}
