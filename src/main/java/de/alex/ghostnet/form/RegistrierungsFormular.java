package de.alex.ghostnet.form;

import java.nio.charset.StandardCharsets;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrierungsFormular {

    @NotBlank(message = "Bitte einen Benutzernamen angeben.")
    @Pattern(regexp = "[A-Za-z0-9._-]{3,30}",
            message = "Der Benutzername braucht 3–30 Zeichen: Buchstaben ohne Umlaute, Zahlen, Punkt, Bindestrich oder Unterstrich.")
    private String benutzername;

    @NotBlank(message = "Bitte den Namen angeben.")
    @Size(max = 80, message = "Der Name darf höchstens 80 Zeichen haben.")
    private String name;

    @NotBlank(message = "Bitte eine Telefonnummer angeben.")
    @Pattern(regexp = "(?=(?:[^0-9]*[0-9]){5})[+0-9 ()/.-]{5,30}",
            message = "Bitte eine Telefonnummer mit mindestens fünf Ziffern und höchstens 30 Zeichen angeben.")
    private String telefon;

    @NotBlank(message = "Bitte ein Passwort angeben.")
    @Size(min = 8, max = 64, message = "Das Passwort braucht 8–64 Zeichen.")
    private String passwort;

    @NotBlank(message = "Bitte das Passwort wiederholen.")
    private String passwortWiederholung;

    @AssertTrue(message = "Die beiden Passwörter stimmen nicht überein.")
    public boolean isPasswortGleich() {
        return passwort == null || passwort.equals(passwortWiederholung);
    }

    @AssertTrue(message = "Das Passwort enthält zu viele mehrbyteige Zeichen. Bitte ein kürzeres Passwort wählen.")
    public boolean isPasswortLaengeGueltig() {
        return passwort == null || passwort.getBytes(StandardCharsets.UTF_8).length <= 72;
    }

    public String getBenutzername() { return benutzername; }
    public void setBenutzername(String benutzername) { this.benutzername = benutzername; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public String getPasswort() { return passwort; }
    public void setPasswort(String passwort) { this.passwort = passwort; }
    public String getPasswortWiederholung() { return passwortWiederholung; }
    public void setPasswortWiederholung(String passwortWiederholung) { this.passwortWiederholung = passwortWiederholung; }
}
