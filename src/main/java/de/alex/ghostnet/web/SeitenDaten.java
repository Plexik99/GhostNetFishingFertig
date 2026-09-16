package de.alex.ghostnet.web;

import java.security.Principal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SeitenDaten {

    @ModelAttribute("angemeldeterName")
    public String angemeldeterName(Principal principal) {
        return principal == null ? null : principal.getName();
    }
}
