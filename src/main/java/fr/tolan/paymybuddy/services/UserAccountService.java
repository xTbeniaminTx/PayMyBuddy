package fr.tolan.paymybuddy.services;

import fr.tolan.paymybuddy.daos.UserAccountRepository;
import fr.tolan.paymybuddy.entities.UserAccount;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

  @Autowired
  AuthenticationManager authenticationManager;

  UserAccountRepository accountRepository;

  public UsernamePasswordAuthenticationToken generateToken(UserAccount user) {
    return new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
  }

  public String authenticateUser(HttpServletRequest req, UserAccount user) {
    try {
      SecurityContext sc = SecurityContextHolder.getContext();
      sc.setAuthentication(authenticationManager.authenticate(this.generateToken(user)));
      HttpSession session = req.getSession(true);
      session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
    } catch (Exception e) {
      e.printStackTrace();

    }
    return "/error";
  }

  public void registerUser(UserAccount user) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
  }

  public Object getConnectedUser() {

    return SecurityContextHolder.getContext().getAuthentication().getPrincipal();

  }

  public boolean isUserNameValid(String username) {
    UserAccount byUserName = accountRepository.findByUserName(username);

    return byUserName != null;
  }

}
