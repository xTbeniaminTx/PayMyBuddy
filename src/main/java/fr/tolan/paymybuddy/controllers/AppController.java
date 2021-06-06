package fr.tolan.paymybuddy.controllers;

import fr.tolan.paymybuddy.daos.UserAccountRepository;
import fr.tolan.paymybuddy.entities.CustomUserDetails;
import fr.tolan.paymybuddy.entities.UserAccount;
import fr.tolan.paymybuddy.services.TransactionServiceImpl;
import fr.tolan.paymybuddy.services.UserAccountService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

  @Autowired
  private UserAccountService accountService;

  @Autowired
  private TransactionServiceImpl transactionServiceImpl;

  @Autowired
  private UserAccountRepository accountRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @GetMapping("/home")
  public String home() {
    return "home";
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
