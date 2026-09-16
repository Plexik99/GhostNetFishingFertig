package de.alex.ghostnet.web;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.alex.ghostnet.form.RegistrierungsFormular;
import de.alex.ghostnet.service.BenutzerService;
import de.alex.ghostnet.service.FachlicherFehler;

@Controller
public class SeitenController {

    private final BenutzerService benutzerService;

    public SeitenController(BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @GetMapping("/")
    public String startseite() {
        return "index";
    }

    @GetMapping("/anmelden")
    public String anmelden() {
        return "anmelden";
    }

    @GetMapping("/registrieren")
    public String registrieren(Model model) {
        model.addAttribute("formular", new RegistrierungsFormular());
        return "registrieren";
    }

    @PostMapping("/registrieren")
    public String kontoSpeichern(@Valid @ModelAttribute("formular") RegistrierungsFormular formular,
            BindingResult fehler, RedirectAttributes weiterleitung) {
        if (fehler.hasErrors()) {
            return "registrieren";
        }
        try {
            benutzerService.registrieren(formular);
        } catch (FachlicherFehler | DataIntegrityViolationException ex) {

            fehler.rejectValue("benutzername", "vergeben", "Dieser Benutzername ist bereits vergeben.");
            return "registrieren";
        }
        weiterleitung.addFlashAttribute("erfolg", "Das Konto wurde angelegt. Du kannst dich jetzt anmelden.");
        return "redirect:/anmelden";
    }
}
