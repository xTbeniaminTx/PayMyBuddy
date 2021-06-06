package fr.tolan.paymybuddy.controllers;

import fr.tolan.paymybuddy.daos.UserAccountRepository;
import fr.tolan.paymybuddy.entities.UserAccount;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  UserAccountRepository accountRepository;

  @GetMapping("/adm")
  public String login() {
    return "security/admin";
  }

  @GetMapping("/login")
  public String showLogin() {

    return "security/login";

  }

  @GetMapping("/register")
  public String register(Model model) {
    UserAccount userAccount = new UserAccount();
    model.addAttribute("userAccount", userAccount);
    return "security/register";
  }

  @PostMapping("/register/save")
  public String saveUser(Model model, UserAccount userAccount) {
    userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
    accountRepository.save(userAccount);
    return "redirect:/home";
  }

  @GetMapping("/access-denied")
  public String showAccessDenied() {

    return "access-denied";

  }

}
