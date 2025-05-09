package br.com.pj2.back.entrypoint.api.config;

import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.StudentGateway;
import br.com.pj2.back.core.gateway.TeacherGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthProviderConfig {

    private final TeacherGateway teacherGateway;
    private final StudentGateway studentGateway;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            try {
                var teacher = teacherGateway.findByRegistration(username);
                var authority = new SimpleGrantedAuthority("ROLE_" + teacher.getRole().name());
                return new User(teacher.getRegistration(), "", List.of(authority));
            } catch (ResourceNotFoundException e) {
                var student = studentGateway.findByRegistration(username);
                var authority = new SimpleGrantedAuthority("ROLE_" + student.getRole().name());
                return new User(student.getRegistration(), "", List.of(authority));
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
