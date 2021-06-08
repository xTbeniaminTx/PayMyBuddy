package fr.tolan.paymybuddy.services;

import fr.tolan.paymybuddy.daos.UserAccountRepository;
import fr.tolan.paymybuddy.entities.CustomUserDetails;
import fr.tolan.paymybuddy.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserAccountRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAccount user = userRepo.findByUserName(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return new CustomUserDetails(user);
  }

}