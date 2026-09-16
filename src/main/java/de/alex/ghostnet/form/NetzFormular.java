package de.alex.ghostnet.form;

import java.math.BigDecimal;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NetzFormular {

    @NotNull(message = "Bitte den Breitengrad angeben.")
    @DecimalMin(value = "-90", message = "Der Breitengrad muss mindestens -90 sein.")
    @DecimalMax(value = "90", message = "Der Breitengrad darf höchstens 90 sein.")
    @Digits(integer = 3, fraction = 6, message = "Beim Breitengrad höchstens sechs Nachkommastellen verwenden.")
    private BigDecimal breitengrad;

    @NotNull(message = "Bitte den Längengrad angeben.")
    @DecimalMin(value = "-180", message = "Der Längengrad muss mindestens -180 sein.")
    @DecimalMax(value = "180", message = "Der Längengrad darf höchstens 180 sein.")
    @Digits(integer = 3, fraction = 6, message = "Beim Längengrad höchstens sechs Nachkommastellen verwenden.")
    private BigDecimal laengengrad;

    @NotNull(message = "Bitte die geschätzte Fläche angeben.")
    @DecimalMin(value = "0.01", message = "Die geschätzte Fläche muss größer als null sein.")
    @Digits(integer = 8, fraction = 2, message = "Die Fläche darf höchstens acht Stellen vor und zwei nach dem Komma haben.")
    private BigDecimal groesseInQuadratmetern;

    private boolean anonym = true;

    @Size(max = 80, message = "Der Name darf höchstens 80 Zeichen haben.")
    private String melderName;

    @Size(max = 30, message = "Die Telefonnummer darf höchstens 30 Zeichen haben.")
    private String melderTelefon;

    @AssertTrue(message = "Bei einer persönlichen Meldung bitte Name und eine Telefonnummer mit mindestens fünf Ziffern angeben.")
    public boolean isKontaktVollstaendig() {
        if (anonym) {
            return true;
        }
        return melderName != null && !melderName.isBlank()
                && melderTelefon != null
                && melderTelefon.trim().matches("(?=(?:[^0-9]*[0-9]){5})[+0-9 ()/.-]{5,30}");
    }

    public BigDecimal getBreitengrad() { return breitengrad; }
    public void setBreitengrad(BigDecimal breitengrad) { this.breitengrad = breitengrad; }
    public BigDecimal getLaengengrad() { return laengengrad; }
    public void setLaengengrad(BigDecimal laengengrad) { this.laengengrad = laengengrad; }
    public BigDecimal getGroesseInQuadratmetern() { return groesseInQuadratmetern; }
    public void setGroesseInQuadratmetern(BigDecimal groesse) { this.groesseInQuadratmetern = groesse; }
    public boolean isAnonym() { return anonym; }
    public void setAnonym(boolean anonym) { this.anonym = anonym; }
    public String getMelderName() { return melderName; }
    public void setMelderName(String melderName) { this.melderName = melderName; }
    public String getMelderTelefon() { return melderTelefon; }
    public void setMelderTelefon(String melderTelefon) { this.melderTelefon = melderTelefon; }
}
