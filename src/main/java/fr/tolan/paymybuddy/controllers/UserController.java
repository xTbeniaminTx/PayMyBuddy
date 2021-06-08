package fr.tolan.paymybuddy.controllers;



import static java.util.stream.StreamSupport.stream;

import fr.tolan.paymybuddy.daos.UserAccountRepository;
import fr.tolan.paymybuddy.entities.Transaction;
import fr.tolan.paymybuddy.entities.UserAccount;
import fr.tolan.paymybuddy.services.TransactionServiceImpl;
import fr.tolan.paymybuddy.services.UserAccountService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javassist.bytecode.stackmap.TypeData.ClassName;
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
    Set<String> usernames= users.stream().map(UserAccount::getUserName).collect(Collectors.toSet());
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
    md.addAttribute("usernames", usernames);
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

  @GetMapping("/profile")
  public ModelAndView profileView(ModelAndView md) throws Exception {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      UserAccount user = accountRepository.findByUserName(((UserDetails) principal).getUsername());
      md.addObject("user", user);
      md.setViewName("user/profile");
      return md;
    } else {
      throw new Exception("Error occured : it seems you are not logged in.");
    }
  }

  @Transactional
  @PostMapping("/transferBank_action")
  public String bankTransferAction(@RequestParam double amount, ModelAndView md) throws Exception {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      UserAccount user = accountRepository.findByUserName(((UserDetails) principal).getUsername());
      entityManager
          .createNativeQuery("UPDATE bank_account SET balance = ? WHERE id_bank_account = ?;")
          .setParameter(1, amount + user.getBankAccount().getBalance())
          .setParameter(2, user.getBankAccount().getIdBankAccount())
          .executeUpdate();
      md.addObject("user", user);
      md.setViewName("user/profile");
      return "redirect:/profile";
    }
    throw new Exception("Error occured : it seems you are not logged in.");
  }

  @GetMapping("/transfer")
  public ModelAndView transferView(ModelAndView md) {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {

      UserAccount user = accountRepository.findByUserName(((UserDetails) principal).getUsername());
      List<Transaction> transactions = transactionServiceImpl.getAllTransactionsByUser(user);

      md.addObject("setOfTransactions", transactions);
      md.addObject("user", user);
      md.setViewName("user/transfer");

      return md;
    }

    return null;
  }

  @Transactional
  @PostMapping("/transfer")
  public String transferAction(
      @RequestParam(value = "beneficiary", required = true) String beneficiary,
      @RequestParam(value = "description", required = true) String description,
      @RequestParam(value = "amount", required = true) double amount, ModelAndView md)
      throws Exception {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      UserAccount user = accountRepository.findByUserName(((UserDetails) principal).getUsername());
      UserAccount beneficiaryUser = accountRepository.findByUserName(beneficiary);
      transactionServiceImpl.transfer(user, beneficiaryUser, description, amount);
      md.addObject("user", user);
      md.setViewName("user/transfer");
      return "redirect:/transfer";
    } else {
      throw new Exception("Error occured : it seems you are not logged in.");
    }
  }


}
