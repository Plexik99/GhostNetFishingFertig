package de.alex.ghostnet.service;

public class FachlicherFehler extends RuntimeException {
    public FachlicherFehler(String nachricht) {
        super(nachricht);
    }
}
