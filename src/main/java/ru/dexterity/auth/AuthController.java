package ru.dexterity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dexterity.auth.AuthException.AuthError;
import ru.dexterity.dao.models.Credential;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthComponent authComponent;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) Boolean error, Model model) {
        if (error != null && error) {
            model.addAttribute("authError", new AuthException(AuthError.CREDENTIAL_INCORRECT, "Неверный логин или пароль"));
        }

        return "login";
    }

    @GetMapping("/sign")
    public String signPage(Model model) {
        model.addAttribute("credential", new Credential());
        return "sign";
    }

    @PostMapping("/sign")
    public String sign(@ModelAttribute("credential") Credential credential, Model model) {
      try {
          authComponent.registerUser(credential);
          return "redirect:/login";
      } catch (AuthException exception) {
          model.addAttribute("credential", credential);
          model.addAttribute("authError", exception);
          return "sign";
      }
    }

}