package de.alex.ghostnet.web;

import java.security.Principal;

import jakarta.validation.Valid;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.alex.ghostnet.form.NetzFormular;
import de.alex.ghostnet.model.Geisternetz;
import de.alex.ghostnet.model.NetzStatus;
import de.alex.ghostnet.service.FachlicherFehler;
import de.alex.ghostnet.service.NetzService;

@Controller
public class NetzController {

    private final NetzService service;

    public NetzController(NetzService service) {
        this.service = service;
    }

    @GetMapping("/netze")
    public String netze(Model model) {
        model.addAttribute("netze", service.offeneNetze());
        model.addAttribute("meineAnsicht", false);
        model.addAttribute("verscholleneAnsicht", false);
        return "netze";
    }

    @GetMapping("/netze/melden")
    public String melden(Model model) {
        model.addAttribute("formular", new NetzFormular());
        return "melden";
    }

    @PostMapping("/netze/melden")
    public String speichern(@Valid @ModelAttribute("formular") NetzFormular formular,
            BindingResult fehler, RedirectAttributes weiterleitung) {
        if (fehler.hasErrors()) {
            return "melden";
        }
        Geisternetz netz = service.melden(formular);
        weiterleitung.addFlashAttribute("erfolg", "Das Netz wurde gemeldet und gespeichert.");
        
        return "redirect:/netze/" + netz.getId();
    }

    @GetMapping("/netze/{id}")
    public String details(@PathVariable Long id, Model model, Principal principal) {
        Geisternetz netz = service.findeNetz(id);
        boolean zustaendig = principal != null && netz.getBergendePerson() != null
                && netz.getBergendePerson().getBenutzername().equals(principal.getName());

        model.addAttribute("netz", netz);
        model.addAttribute("zustaendig", zustaendig);
        model.addAttribute("uebernehmbar", netz.getStatus() == NetzStatus.GEMELDET
                && netz.getBergendePerson() == null);
        model.addAttribute("abschliessbar", zustaendig
                && netz.getStatus() == NetzStatus.BERGUNG_BEVORSTEHEND);
        model.addAttribute("verschollenMeldbar", netz.getStatus() == NetzStatus.GEMELDET
                || netz.getStatus() == NetzStatus.BERGUNG_BEVORSTEHEND);
        return "details";
    }

    @PostMapping("/netze/{id}/uebernehmen")
    public String uebernehmen(@PathVariable Long id, Principal principal, RedirectAttributes weiterleitung) {
        try {
            service.uebernehmen(id, principal.getName());
            weiterleitung.addFlashAttribute("erfolg", "Du bist jetzt für diese Bergung eingetragen.");
        } catch (FachlicherFehler ex) {
            weiterleitung.addFlashAttribute("fehler", ex.getMessage());
        } catch (ObjectOptimisticLockingFailureException ex) {
            weiterleitung.addFlashAttribute("fehler", "Das Netz wurde gleichzeitig von jemand anderem geändert. Bitte prüfe den aktuellen Stand.");
        }
        return "redirect:/netze/" + id;
    }

    @PostMapping("/netze/{id}/abschliessen")
    public String abschliessen(@PathVariable Long id, Principal principal, RedirectAttributes weiterleitung) {
        try {
            service.abschliessen(id, principal.getName());
            weiterleitung.addFlashAttribute("erfolg", "Die erfolgreiche Bergung wurde gespeichert.");
        } catch (FachlicherFehler ex) {
            weiterleitung.addFlashAttribute("fehler", ex.getMessage());
        } catch (ObjectOptimisticLockingFailureException ex) {
            weiterleitung.addFlashAttribute("fehler", "Das Netz wurde inzwischen geändert. Bitte prüfe den aktuellen Stand.");
        }
        return "redirect:/netze/" + id;
    }

    @GetMapping("/meine-bergungen")
    public String meineBergungen(Model model, Principal principal) {
        model.addAttribute("netze", service.meineBergungen(principal.getName()));
        model.addAttribute("meineAnsicht", true);
        model.addAttribute("verscholleneAnsicht", false);
        return "netze";
    }

    @GetMapping("/verschollene-netze")
    public String verscholleneNetze(Model model) {
        model.addAttribute("netze", service.verscholleneNetze());
        model.addAttribute("meineAnsicht", false);
        model.addAttribute("verscholleneAnsicht", true);
        return "netze";
    }

    @PostMapping("/netze/{id}/verschollen")
    public String verschollenMelden(@PathVariable Long id, Principal principal,
            RedirectAttributes weiterleitung) {
        try {
            service.alsVerschollenMelden(id, principal.getName());
            weiterleitung.addFlashAttribute("erfolg", "Das Netz wurde als nicht mehr auffindbar gemeldet. Sein Status ist jetzt Verschollen.");
        } catch (FachlicherFehler ex) {
            weiterleitung.addFlashAttribute("fehler", ex.getMessage());
        } catch (ObjectOptimisticLockingFailureException ex) {
            weiterleitung.addFlashAttribute("fehler", "Das Netz wurde inzwischen geändert. Bitte prüfe den aktuellen Stand.");
        }
        return "redirect:/netze/" + id;
    }
}
