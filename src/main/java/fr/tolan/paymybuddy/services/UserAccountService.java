package fr.tolan.paymybuddy.services;

import fr.tolan.paymybuddy.entities.UserAccount;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	public UsernamePasswordAuthenticationToken generateToken(UserAccount user) {
		return  new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
	}
	
	public void authenticateUser(HttpServletRequest req, UserAccount user) {
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(authenticationManager.authenticate(this.generateToken(user)));
		HttpSession session = req.getSession(true);
	    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
	}
	
//	public void registerUser(UserAccount user) {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String encodedPassword = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encodedPassword);
//	}

	
}
