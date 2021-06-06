package fr.tolan.paymybuddy.controllers;

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

}
