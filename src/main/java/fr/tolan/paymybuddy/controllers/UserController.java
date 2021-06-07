package fr.tolan.paymybuddy.controllers;

import fr.tolan.paymybuddy.daos.UserAccountRepository;
import fr.tolan.paymybuddy.entities.UserAccount;
import fr.tolan.paymybuddy.services.TransactionServiceImpl;
import fr.tolan.paymybuddy.services.UserAccountService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

  @Autowired
  private TransactionServiceImpl transactionServiceImpl;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private UserAccountService accountService;

  @Autowired
  private UserAccountRepository accountRepository;

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/contact")
  public String contactView(Model md) {

    List<UserAccount> userThatCanBeAdded = new ArrayList<>();

    UserAccount principalUser = accountRepository.findByUserName(
        ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            .getUsername());
    List<UserAccount> users = (List<UserAccount>) accountRepository.findAll();
    Set<UserAccount> contactListOfPrincipalUser = principalUser.getContacts();
    if (!contactListOfPrincipalUser.isEmpty()) {
      for (UserAccount user : users) {
        for (UserAccount contact : contactListOfPrincipalUser) {
          if (!user.getUserName().equals(contact.getUserName())) {
            userThatCanBeAdded.add(user);
          }
        }
      }
    }

    md.addAttribute("users", userThatCanBeAdded);
    md.addAttribute("user", principalUser);
    return "user/contact";
  }

  @Transactional
  @PostMapping("/contact_action")
  public String contactAction(
      @RequestParam(value = "connection", required = true) String connectionAsString,
      ModelAndView md) throws Exception {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      UserAccount userPrincipal = accountRepository
          .findByUserName(((UserDetails) principal).getUsername());
      UserAccount connection = accountRepository.findByUserName(connectionAsString);

      if (connection != null) {
        if (!transactionServiceImpl.verifyIsAContact(connection, userPrincipal)) {
          entityManager
              .createNativeQuery("INSERT INTO contacts (first_user, second_user) VALUES (?,?)")
              .setParameter(1, userPrincipal.getId()).setParameter(2, connection.getId())
              .executeUpdate();
          md.addObject("user", userPrincipal);
          md.addObject("connection", connection);
          md.setViewName("user/contact");
          return "redirect:/contact";
        } else {
          throw new Exception("You already have this user as a connection.");
        }
      } else {
        throw new Exception("This user do not exist.");
      }
    }
    throw new Exception("Error occured : it seems you are not logged in.");
  }




}
