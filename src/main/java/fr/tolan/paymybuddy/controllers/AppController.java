package fr.tolan.paymybuddy.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

//  @GetMapping("/admin")
//  public String admin() {
//    return "403";
//  }

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
