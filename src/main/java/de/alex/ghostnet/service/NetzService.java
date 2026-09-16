package de.alex.ghostnet.service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import de.alex.ghostnet.form.NetzFormular;
import de.alex.ghostnet.model.Benutzer;
import de.alex.ghostnet.model.Geisternetz;
import de.alex.ghostnet.model.NetzStatus;
import de.alex.ghostnet.repository.NetzRepository;

@Service
@Validated
@Transactional(readOnly = true)
public class NetzService {

    private final NetzRepository repository;
    private final BenutzerService benutzerService;

    public NetzService(NetzRepository repository, BenutzerService benutzerService) {
        this.repository = repository;
        this.benutzerService = benutzerService;
    }

    @Transactional
    public Geisternetz melden(@Valid NetzFormular formular) {
        
        String name = formular.isAnonym() ? null : formular.getMelderName().trim();
        String telefon = formular.isAnonym() ? null : formular.getMelderTelefon().trim();

        Geisternetz netz = new Geisternetz(formular.getBreitengrad(), formular.getLaengengrad(),
                formular.getGroesseInQuadratmetern(), name, telefon);
        return repository.saveAndFlush(netz);
    }

    public Geisternetz findeNetz(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Netz nicht gefunden."));
    }

    public List<Geisternetz> offeneNetze() {
        return repository.findByStatusInOrderByGemeldetAmDesc(
                List.of(NetzStatus.GEMELDET, NetzStatus.BERGUNG_BEVORSTEHEND));
    }

    public List<Geisternetz> verscholleneNetze() {
        return repository.findByStatusOrderByGemeldetAmDesc(NetzStatus.VERSCHOLLEN);
    }

    public List<Geisternetz> meineBergungen(String benutzername) {
        Benutzer benutzer = benutzerService.findeBenutzer(benutzername);
        return repository.findByBergendePersonIdOrderByGemeldetAmDesc(benutzer.getId());
    }

    @Transactional
    public void uebernehmen(Long netzId, String benutzername) {
        Geisternetz netz = findeNetz(netzId);
        if (netz.getStatus() != NetzStatus.GEMELDET || netz.getBergendePerson() != null) {
            throw new FachlicherFehler("Dieses Netz kann nicht mehr übernommen werden.");
        }
        netz.setBergendePerson(benutzerService.findeBenutzer(benutzername));
        netz.setStatus(NetzStatus.BERGUNG_BEVORSTEHEND);
        
        repository.saveAndFlush(netz);
    }

    @Transactional
    public void abschliessen(Long netzId, String benutzername) {
        Geisternetz netz = findeNetz(netzId);
        Benutzer benutzer = benutzerService.findeBenutzer(benutzername);

        if (netz.getBergendePerson() == null
                || !netz.getBergendePerson().getId().equals(benutzer.getId())) {
            throw new FachlicherFehler("Nur die zuständige Person kann diese Bergung abschließen.");
        }
        if (netz.getStatus() != NetzStatus.BERGUNG_BEVORSTEHEND) {
            throw new FachlicherFehler("Diese Bergung kann nicht erneut abgeschlossen werden.");
        }
        netz.setStatus(NetzStatus.GEBORGEN);
        netz.setGeborgenAm(LocalDateTime.now());
        repository.saveAndFlush(netz);
    }

    @Transactional
    public void alsVerschollenMelden(Long netzId, String benutzername) {
        Geisternetz netz = findeNetz(netzId);
        if (netz.getStatus() != NetzStatus.GEMELDET
                && netz.getStatus() != NetzStatus.BERGUNG_BEVORSTEHEND) {
            throw new FachlicherFehler("Nur ein offenes Netz kann als nicht mehr auffindbar gemeldet werden.");
        }
        
        Benutzer person = benutzerService.findeBenutzer(benutzername);
        netz.setStatus(NetzStatus.VERSCHOLLEN);
        netz.setVerschollenGemeldetVon(person);
        netz.setVerschollenAm(LocalDateTime.now());
        
        repository.saveAndFlush(netz);
    }
}
