package de.alex.ghostnet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SicherheitsKonfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(regeln -> regeln
                .requestMatchers("/", "/netze", "/netze/melden", "/verschollene-netze",
                        "/registrieren", "/css/**", "/js/**", "/favicon.svg", "/error")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/netze/*").permitAll()
                .anyRequest().authenticated());

        http.formLogin(anmeldung -> anmeldung
                .loginPage("/anmelden")
                .loginProcessingUrl("/anmelden")
                .defaultSuccessUrl("/meine-bergungen", true)
                .permitAll());

        http.logout(abmeldung -> abmeldung
                .logoutUrl("/abmelden")
                .logoutSuccessUrl("/?abgemeldet"));

        return http.build();
    }
}
