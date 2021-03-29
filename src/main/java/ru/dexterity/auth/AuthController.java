package ru.dexterity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dexterity.dao.models.Credential;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthComponent authComponent;

    @GetMapping("/login")
    public String loginPage() {
        return "log";
    }

    @GetMapping("/sign")
    public String signPage(Model model) {
        Credential credential = new Credential();
        model.addAttribute("credential", credential);

        return "sign";
    }

    @PostMapping("/sign")
    public String sign(@ModelAttribute("credential") Credential credential) {
      log.info(authComponent.registerUser(credential).toString());
      return "redirect:/login";
    }

}
