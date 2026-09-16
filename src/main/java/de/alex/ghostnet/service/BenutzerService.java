package de.alex.ghostnet.service;

import java.util.Locale;

import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import de.alex.ghostnet.form.RegistrierungsFormular;
import de.alex.ghostnet.model.Benutzer;
import de.alex.ghostnet.repository.BenutzerRepository;

@Service
@Validated
@Transactional(readOnly = true)
public class BenutzerService implements UserDetailsService {

    private final BenutzerRepository repository;
    private final PasswordEncoder passwordEncoder;

    public BenutzerService(BenutzerRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Benutzer registrieren(@Valid RegistrierungsFormular formular) {
        String benutzername = formular.getBenutzername().toLowerCase(Locale.ROOT);
        if (repository.existsByBenutzername(benutzername)) {
            throw new FachlicherFehler("Dieser Benutzername ist bereits vergeben.");
        }

        Benutzer benutzer = new Benutzer(benutzername, formular.getName().trim(),
                formular.getTelefon().trim(), passwordEncoder.encode(formular.getPasswort()));
        return repository.saveAndFlush(benutzer);
    }

    public Benutzer findeBenutzer(String benutzername) {
        return repository.findByBenutzername(benutzername.toLowerCase(Locale.ROOT))
                .orElseThrow(() -> new UsernameNotFoundException("Das Konto wurde nicht gefunden."));
    }

    @Override
    public UserDetails loadUserByUsername(String benutzername) throws UsernameNotFoundException {
        Benutzer benutzer = findeBenutzer(benutzername);
        return User.withUsername(benutzer.getBenutzername())
                .password(benutzer.getPasswortHash())
                .roles("BERGER")
                .build();
    }
}
