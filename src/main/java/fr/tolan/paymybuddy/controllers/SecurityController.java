package fr.tolan.paymybuddy.controllers;

import fr.tolan.paymybuddy.daos.UserAccountRepository;
import fr.tolan.paymybuddy.entities.UserAccount;
import fr.tolan.paymybuddy.services.UserAccountService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final Logger logger = LoggerFactory.getLogger(SecurityController.class);

  @Autowired
  UserAccountService userAccountService;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  UserAccountRepository accountRepository;


  @GetMapping("/login")
  public ModelAndView login(ModelAndView md, UserAccount user) {
    md.addObject("user", user);
    md.setViewName("security/login");
    return md;
  }

  @PostMapping("/dashboard")
  public String loginAction(HttpServletRequest req, UserAccount user) {
    userAccountService.authenticateUser(req, user);
    if (SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal() instanceof UserDetails) {
      return "user/dashboardUser";
    }
    return "redirect:/error";
  }

  @GetMapping("/register")
  public String register(Model model) {
    UserAccount userAccount = new UserAccount();
    model.addAttribute("userAccount", userAccount);
    return "security/register";
  }

  @PostMapping("/register_action")
  public String register(UserAccount user) throws Exception {
    userAccountService.registerUser(user);
    if (accountRepository.findByUserName(user.getUserName()) == null) {
      accountRepository.save(user);
      return "register_success";
    } else {
      return "redirect:/error";
    }
  }

  @GetMapping("/access-denied")
  public String showAccessDenied() {

    return "access-denied";

  }

  @GetMapping("/logout")
  public String fetchSignoutSite(HttpServletRequest request) throws Exception {
    SecurityContextHolder.clearContext();
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
      return "home";
    } else {
      return "redirect:/error";
    }
  }

}
