package de.alex.ghostnet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import de.alex.ghostnet.model.Benutzer;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    Optional<Benutzer> findByBenutzername(String benutzername);
    boolean existsByBenutzername(String benutzername);
}
