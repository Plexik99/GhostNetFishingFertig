package de.alex.ghostnet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "benutzer", uniqueConstraints =
        @UniqueConstraint(name = "uk_benutzername", columnNames = "benutzername"))
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String benutzername;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 30)
    private String telefon;

    @Column(name = "passwort_hash", nullable = false, length = 100)
    private String passwortHash;

    protected Benutzer() {
        
    }

    public Benutzer(String benutzername, String name, String telefon, String passwortHash) {
        this.benutzername = benutzername;
        this.name = name;
        this.telefon = telefon;
        this.passwortHash = passwortHash;
    }

    public Long getId() { return id; }
    public String getBenutzername() { return benutzername; }
    public String getName() { return name; }
    public String getTelefon() { return telefon; }
    public String getPasswortHash() { return passwortHash; }
}
