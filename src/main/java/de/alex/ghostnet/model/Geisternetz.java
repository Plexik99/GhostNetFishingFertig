package de.alex.ghostnet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class Geisternetz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal breitengrad;

    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal laengengrad;

    @Column(name = "groesse_qm", nullable = false, precision = 10, scale = 2)
    private BigDecimal groesseInQuadratmetern;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NetzStatus status;

    @Column(length = 80)
    private String melderName;

    @Column(length = 30)
    private String melderTelefon;

    @ManyToOne
    @JoinColumn(name = "bergende_person_id")
    private Benutzer bergendePerson;

    @Column(nullable = false)
    private LocalDateTime gemeldetAm;

    private LocalDateTime geborgenAm;

    @ManyToOne
    @JoinColumn(name = "verschollen_gemeldet_von_id")
    private Benutzer verschollenGemeldetVon;

    private LocalDateTime verschollenAm;

    protected Geisternetz() {
    }

    public Geisternetz(BigDecimal breitengrad, BigDecimal laengengrad,
            BigDecimal groesseInQuadratmetern, String melderName, String melderTelefon) {
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
        this.groesseInQuadratmetern = groesseInQuadratmetern;
        this.melderName = melderName;
        this.melderTelefon = melderTelefon;
        this.status = NetzStatus.GEMELDET;
        this.gemeldetAm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getVersion() { return version; }
    public BigDecimal getBreitengrad() { return breitengrad; }
    public BigDecimal getLaengengrad() { return laengengrad; }
    public BigDecimal getGroesseInQuadratmetern() { return groesseInQuadratmetern; }
    public NetzStatus getStatus() { return status; }
    public String getMelderName() { return melderName; }
    public String getMelderTelefon() { return melderTelefon; }
    public Benutzer getBergendePerson() { return bergendePerson; }
    public LocalDateTime getGemeldetAm() { return gemeldetAm; }
    public LocalDateTime getGeborgenAm() { return geborgenAm; }
    public Benutzer getVerschollenGemeldetVon() { return verschollenGemeldetVon; }
    public LocalDateTime getVerschollenAm() { return verschollenAm; }

    public void setStatus(NetzStatus status) { this.status = status; }
    public void setBergendePerson(Benutzer bergendePerson) { this.bergendePerson = bergendePerson; }
    public void setGeborgenAm(LocalDateTime geborgenAm) { this.geborgenAm = geborgenAm; }
    public void setVerschollenGemeldetVon(Benutzer person) { this.verschollenGemeldetVon = person; }
    public void setVerschollenAm(LocalDateTime zeitpunkt) { this.verschollenAm = zeitpunkt; }
}
