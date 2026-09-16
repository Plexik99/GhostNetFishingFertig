package de.alex.ghostnet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import de.alex.ghostnet.form.NetzFormular;
import de.alex.ghostnet.form.RegistrierungsFormular;
import de.alex.ghostnet.model.Benutzer;
import de.alex.ghostnet.model.Geisternetz;
import de.alex.ghostnet.model.NetzStatus;
import de.alex.ghostnet.repository.BenutzerRepository;
import de.alex.ghostnet.repository.NetzRepository;
import de.alex.ghostnet.service.BenutzerService;
import de.alex.ghostnet.service.FachlicherFehler;
import de.alex.ghostnet.service.NetzService;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:ghostnet-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "logging.level.root=WARN"
})
@AutoConfigureMockMvc
class GhostNetIntegrationTest {

    @Autowired MockMvc web;
    @Autowired NetzService netzService;
    @Autowired BenutzerService benutzerService;
    @Autowired NetzRepository netze;
    @Autowired BenutzerRepository benutzer;
    @Autowired PasswordEncoder passwoerter;
    @Autowired PlatformTransactionManager transaktionen;

    @BeforeEach
    void testdatenLeeren() {
        netze.deleteAll();
        benutzer.deleteAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/netze", "/netze/melden", "/verschollene-netze", "/anmelden", "/registrieren"})
    void oeffentlicheSeitenWerdenGerendert(String adresse) throws Exception {
        web.perform(get(adresse)).andExpect(status().isOk())
                .andExpect(content().string(containsString("Ghost Net Fishing")));
    }

    @Test
    void anonymeMeldungSpeichertAuchBeiManipulationKeinenKontakt() {
        NetzFormular formular = netzFormular();
        formular.setMelderName("Nicht speichern");
        formular.setMelderTelefon("01234 56789");
        Geisternetz netz = netzService.melden(formular);
        Geisternetz gespeichert = netzService.findeNetz(netz.getId());
        assertThat(gespeichert.getMelderName()).isNull();
        assertThat(gespeichert.getMelderTelefon()).isNull();
        assertThat(gespeichert.getStatus()).isEqualTo(NetzStatus.GEMELDET);
    }

    @Test
    void persoenlicheMeldungBrauchtBeideKontaktangaben() {
        NetzFormular formular = netzFormular();
        formular.setAnonym(false);
        formular.setMelderName("Testperson");
        assertThatThrownBy(() -> netzService.melden(formular))
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(netze.count()).isZero();
        formular.setMelderTelefon("01234 56789");
        assertThat(netzService.melden(formular).getMelderName()).isEqualTo("Testperson");
    }

    @Test
    void serverLehntFalscheKoordinatenUndFlaechenAb() {
        NetzFormular formular = netzFormular();
        formular.setBreitengrad(new BigDecimal("90.000001"));
        assertThatThrownBy(() -> netzService.melden(formular)).isInstanceOf(ConstraintViolationException.class);
        formular.setBreitengrad(new BigDecimal("90"));
        formular.setLaengengrad(new BigDecimal("180.000001"));
        assertThatThrownBy(() -> netzService.melden(formular)).isInstanceOf(ConstraintViolationException.class);
        formular.setLaengengrad(new BigDecimal("-180"));
        formular.setGroesseInQuadratmetern(BigDecimal.ZERO);
        assertThatThrownBy(() -> netzService.melden(formular)).isInstanceOf(ConstraintViolationException.class);
        assertThat(netze.count()).isZero();
        formular.setGroesseInQuadratmetern(new BigDecimal("0.01"));
        assertThat(netzService.melden(formular).getId()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1....", "12 34"})
    void kontaktangabenBrauchenMindestensFuenfZiffern(String telefon) {
        NetzFormular meldung = netzFormular();
        meldung.setAnonym(false);
        meldung.setMelderName("Testperson");
        meldung.setMelderTelefon(telefon);
        assertThatThrownBy(() -> netzService.melden(meldung))
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(netze.count()).isZero();

        RegistrierungsFormular registrierung = kontoFormular("test.a");
        registrierung.setTelefon(telefon);
        assertThatThrownBy(() -> benutzerService.registrieren(registrierung))
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(benutzer.count()).isZero();

        meldung.setMelderTelefon("+49 (30) 12345-67");
        assertThat(netzService.melden(meldung).getMelderTelefon()).isEqualTo("+49 (30) 12345-67");
        registrierung.setTelefon("+49 (30) 12345-67");
        assertThat(benutzerService.registrieren(registrierung).getTelefon()).isEqualTo("+49 (30) 12345-67");
    }

    @Test
    void formularZeigtServerfehlerUndSchuetztPostMitCsrf() throws Exception {
        web.perform(get("/netze/melden")).andExpect(content().string(containsString("name=\"_csrf\"")));
        web.perform(post("/netze/melden")).andExpect(status().isForbidden());
        web.perform(post("/netze/melden").with(csrf())
                .param("breitengrad", "abc").param("laengengrad", "12.09")
                .param("groesseInQuadratmetern", "20").param("anonym", "true"))
                .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("formular", "breitengrad"));
        assertThat(netze.count()).isZero();
        web.perform(post("/netze/melden").with(csrf())
                .param("breitengrad", "54.18").param("laengengrad", "12.09")
                .param("groesseInQuadratmetern", "20").param("anonym", "true"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("/netze/*"));
        assertThat(netze.count()).isEqualTo(1);
    }

    @Test
    void kontoSpeichertPasswortAlsHashUndBenutzernamenEindeutig() {
        RegistrierungsFormular formular = kontoFormular("Test.A");
        benutzerService.registrieren(formular);
        Benutzer gespeichert = benutzerService.findeBenutzer("test.a");
        assertThat(gespeichert.getPasswortHash()).isNotEqualTo(formular.getPasswort());
        assertThat(passwoerter.matches(formular.getPasswort(), gespeichert.getPasswortHash())).isTrue();
        assertThatThrownBy(() -> benutzerService.registrieren(kontoFormular("TEST.A")))
                .isInstanceOf(FachlicherFehler.class);
    }

    @Test
    void passwortwiederholungUndBytegrenzeWerdenGeprueft() {
        RegistrierungsFormular formular = kontoFormular("test.a");
        formular.setPasswortWiederholung("EtwasAnderes");
        assertThatThrownBy(() -> benutzerService.registrieren(formular))
                .isInstanceOf(ConstraintViolationException.class);
        formular.setPasswort("ä".repeat(40));
        formular.setPasswortWiederholung(formular.getPasswort());
        assertThatThrownBy(() -> benutzerService.registrieren(formular))
                .isInstanceOf(ConstraintViolationException.class);
        assertThat(benutzer.count()).isZero();
    }

    @Test
    void registrierungUndEchteAnmeldungFunktionieren() throws Exception {
        web.perform(post("/registrieren").with(csrf()).param("benutzername", "test.a")
                .param("name", "Testperson A").param("telefon", "01234 56789")
                .param("passwort", "NurZumTest123!").param("passwortWiederholung", "NurZumTest123!"))
                .andExpect(redirectedUrl("/anmelden"));
        web.perform(post("/anmelden").with(csrf()).param("username", "test.a").param("password", "falsch"))
                .andExpect(redirectedUrl("/anmelden?error"));
        web.perform(post("/anmelden").with(csrf()).param("username", "TEST.A").param("password", "NurZumTest123!"))
                .andExpect(redirectedUrl("/meine-bergungen"));
    }

    @Test
    void nurAngemeldetePersonenKoennenBergungenUebernehmen() throws Exception {
        Long id = netzService.melden(netzFormular()).getId();
        web.perform(post("/netze/" + id + "/uebernehmen").with(csrf()))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/anmelden"));
        assertThat(netzService.findeNetz(id).getBergendePerson()).isNull();
    }

    @Test
    void einePersonKannMehrereNetzeUebernehmenAberNurEigeneAbschliessen() {
        benutzerService.registrieren(kontoFormular("test.a"));
        benutzerService.registrieren(kontoFormular("test.b"));
        Long erstes = netzService.melden(netzFormular()).getId();
        Long zweites = netzService.melden(netzFormular()).getId();
        netzService.uebernehmen(erstes, "test.a");
        netzService.uebernehmen(zweites, "test.a");
        assertThat(netzService.meineBergungen("test.a")).hasSize(2);
        assertThatThrownBy(() -> netzService.uebernehmen(erstes, "test.b")).isInstanceOf(FachlicherFehler.class);
        assertThatThrownBy(() -> netzService.abschliessen(erstes, "test.b")).isInstanceOf(FachlicherFehler.class);
        assertThat(netzService.findeNetz(erstes).getStatus()).isEqualTo(NetzStatus.BERGUNG_BEVORSTEHEND);
        netzService.abschliessen(erstes, "test.a");
        assertThat(netzService.findeNetz(erstes).getStatus()).isEqualTo(NetzStatus.GEBORGEN);
        assertThat(netzService.findeNetz(erstes).getGeborgenAm()).isNotNull();
        assertThatThrownBy(() -> netzService.abschliessen(erstes, "test.a")).isInstanceOf(FachlicherFehler.class);
        assertThat(netzService.offeneNetze()).extracting(Geisternetz::getId).containsExactly(zweites);
    }

    @Test
    void kontaktBleibtFuerAndereImHtmlVerborgen() throws Exception {
        benutzerService.registrieren(kontoFormular("test.a"));
        benutzerService.registrieren(kontoFormular("test.b"));
        NetzFormular formular = netzFormular();
        formular.setAnonym(false);
        formular.setMelderName("Privater Testkontakt");
        formular.setMelderTelefon("01234 987654");
        Long id = netzService.melden(formular).getId();
        netzService.uebernehmen(id, "test.a");
        web.perform(get("/netze/" + id)).andExpect(status().isOk())
                .andExpect(content().string(not(containsString("Privater Testkontakt"))))
                .andExpect(content().string(not(containsString("01234 987654"))));
        web.perform(get("/netze/" + id).with(user("test.b")))
                .andExpect(content().string(not(containsString("Privater Testkontakt"))));
        web.perform(get("/netze/" + id).with(user("test.a")))
                .andExpect(content().string(containsString("Privater Testkontakt")))
                .andExpect(content().string(containsString("Als geborgen markieren")));
        netzService.abschliessen(id, "test.a");
        assertThat(netzService.offeneNetze()).isEmpty();
    }

    @Test
    void jedeRegistriertePersonKannEinOffenesNetzAlsVerschollenMelden() throws Exception {
        benutzerService.registrieren(kontoFormular("test.a"));
        benutzerService.registrieren(kontoFormular("test.b"));
        Long id = netzService.melden(netzFormular()).getId();
        netzService.uebernehmen(id, "test.a");
        web.perform(post("/netze/" + id + "/verschollen").with(user("test.b")).with(csrf()))
                .andExpect(redirectedUrl("/netze/" + id)).andExpect(flash().attributeExists("erfolg"));
        Geisternetz gespeichert = netzService.findeNetz(id);
        assertThat(gespeichert.getStatus()).isEqualTo(NetzStatus.VERSCHOLLEN);
        assertThat(gespeichert.getVerschollenGemeldetVon().getBenutzername()).isEqualTo("test.b");
        assertThat(gespeichert.getVerschollenAm()).isNotNull();
        assertThat(gespeichert.getBergendePerson().getBenutzername()).isEqualTo("test.a");
        assertThat(netzService.offeneNetze()).isEmpty();
        assertThat(netzService.verscholleneNetze()).extracting(Geisternetz::getId).containsExactly(id);
        assertThat(netzService.meineBergungen("test.a")).hasSize(1);
        web.perform(get("/netze/" + id)).andExpect(status().isOk())
                .andExpect(content().string(containsString("Verschollen")))
                .andExpect(content().string(not(containsString("Testperson test.b"))))
                .andExpect(content().string(not(containsString("01234 56789"))));
    }

    @Test
    void verschollenmeldungOhneBergungszuordnungIstMoeglich() {
        benutzerService.registrieren(kontoFormular("test.a"));
        Long id = netzService.melden(netzFormular()).getId();
        netzService.alsVerschollenMelden(id, "test.a");
        assertThat(netzService.findeNetz(id).getBergendePerson()).isNull();
        assertThat(netzService.findeNetz(id).getVerschollenGemeldetVon()).isNotNull();
        assertThatThrownBy(() -> netzService.uebernehmen(id, "test.a")).isInstanceOf(FachlicherFehler.class);
        assertThatThrownBy(() -> netzService.alsVerschollenMelden(id, "test.a")).isInstanceOf(FachlicherFehler.class);
    }

    @Test
    void verschollenmeldungBrauchtAnmeldungUndCsrfToken() throws Exception {
        benutzerService.registrieren(kontoFormular("test.a"));
        Long id = netzService.melden(netzFormular()).getId();
        web.perform(post("/netze/" + id + "/verschollen").with(csrf()))
                .andExpect(redirectedUrlPattern("**/anmelden"));
        web.perform(post("/netze/" + id + "/verschollen").with(user("test.a")))
                .andExpect(status().isForbidden());
        assertThat(netzService.findeNetz(id).getStatus()).isEqualTo(NetzStatus.GEMELDET);
        assertThat(netzService.findeNetz(id).getVerschollenGemeldetVon()).isNull();
    }

    @Test
    void geborgeneUndVerscholleneNetzeErlaubenKeineWeiterenAbschluesse() throws Exception {
        benutzerService.registrieren(kontoFormular("test.a"));
        Long geborgen = netzService.melden(netzFormular()).getId();
        netzService.uebernehmen(geborgen, "test.a");
        netzService.abschliessen(geborgen, "test.a");
        web.perform(post("/netze/" + geborgen + "/verschollen").with(user("test.a")).with(csrf()))
                .andExpect(flash().attributeExists("fehler"));
        assertThat(netzService.findeNetz(geborgen).getStatus()).isEqualTo(NetzStatus.GEBORGEN);
        assertThat(netzService.findeNetz(geborgen).getVerschollenAm()).isNull();
        Long verschollen = netzService.melden(netzFormular()).getId();
        netzService.uebernehmen(verschollen, "test.a");
        netzService.alsVerschollenMelden(verschollen, "test.a");
        assertThatThrownBy(() -> netzService.abschliessen(verschollen, "test.a")).isInstanceOf(FachlicherFehler.class);
        assertThat(netzService.findeNetz(verschollen).getGeborgenAm()).isNull();
    }

    @Test
    void bergungsaktionenFunktionierenUeberWebUndWeisenFremdeAb() throws Exception {
        benutzerService.registrieren(kontoFormular("test.a"));
        benutzerService.registrieren(kontoFormular("test.b"));
        Long id = netzService.melden(netzFormular()).getId();
        web.perform(post("/netze/" + id + "/uebernehmen").with(user("test.a")).with(csrf()))
                .andExpect(redirectedUrl("/netze/" + id)).andExpect(flash().attributeExists("erfolg"));
        web.perform(get("/meine-bergungen").with(user("test.a")))
                .andExpect(status().isOk()).andExpect(content().string(containsString("Bergung bevorstehend")));
        web.perform(post("/netze/" + id + "/abschliessen").with(user("test.b")).with(csrf()))
                .andExpect(flash().attributeExists("fehler"));
        assertThat(netzService.findeNetz(id).getStatus()).isEqualTo(NetzStatus.BERGUNG_BEVORSTEHEND);
        web.perform(post("/netze/" + id + "/abschliessen").with(user("test.a")).with(csrf()))
                .andExpect(flash().attributeExists("erfolg"));
        assertThat(netzService.findeNetz(id).getStatus()).isEqualTo(NetzStatus.GEBORGEN);
    }

    @Test
    void paralleleAenderungenKoennenEineZuordnungNichtUeberschreiben() throws Exception {
        benutzerService.registrieren(kontoFormular("test.a"));
        benutzerService.registrieren(kontoFormular("test.b"));
        Long id = netzService.melden(netzFormular()).getId();
        CountDownLatch beideGelesen = new CountDownLatch(2);
        var ausfuehrer = Executors.newFixedThreadPool(2);
        try {
            
            Future<Boolean> a = ausfuehrer.submit(() -> gleichzeitigZuordnen(id, "test.a", beideGelesen));
            Future<Boolean> b = ausfuehrer.submit(() -> gleichzeitigZuordnen(id, "test.b", beideGelesen));
            assertThat(List.of(a.get(15, TimeUnit.SECONDS), b.get(15, TimeUnit.SECONDS)))
                    .containsExactlyInAnyOrder(true, false);
            assertThat(netzService.findeNetz(id).getStatus()).isEqualTo(NetzStatus.BERGUNG_BEVORSTEHEND);
            assertThat(netzService.findeNetz(id).getBergendePerson()).isNotNull();
        } finally {
            ausfuehrer.shutdownNow();
        }
    }

    @Test
    void unbekannteNetznummerErgibt404() throws Exception {
        web.perform(get("/netze/999999999")).andExpect(status().isNotFound());
    }

    private boolean gleichzeitigZuordnen(Long id, String name, CountDownLatch start) {
        try {
            new TransactionTemplate(transaktionen).executeWithoutResult(transaktion -> {
                Geisternetz netz = netze.findById(id).orElseThrow();
                Benutzer person = benutzer.findByBenutzername(name).orElseThrow();
                start.countDown();
                try {
                    if (!start.await(5, TimeUnit.SECONDS)) {
                        throw new IllegalStateException("Zweite Testtransaktion fehlt.");
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException(ex);
                }
                netz.setBergendePerson(person);
                netz.setStatus(NetzStatus.BERGUNG_BEVORSTEHEND);
                netze.saveAndFlush(netz);
            });
            return true;
        } catch (ObjectOptimisticLockingFailureException ex) {
            return false;
        }
    }

    private NetzFormular netzFormular() {
        NetzFormular formular = new NetzFormular();
        formular.setBreitengrad(new BigDecimal("54.18"));
        formular.setLaengengrad(new BigDecimal("12.09"));
        formular.setGroesseInQuadratmetern(new BigDecimal("20.50"));
        formular.setAnonym(true);
        return formular;
    }

    private RegistrierungsFormular kontoFormular(String name) {
        RegistrierungsFormular formular = new RegistrierungsFormular();
        formular.setBenutzername(name);
        formular.setName("Testperson " + name);
        formular.setTelefon("01234 56789");
        formular.setPasswort("NurZumTest123!");
        formular.setPasswortWiederholung("NurZumTest123!");
        return formular;
    }
}
