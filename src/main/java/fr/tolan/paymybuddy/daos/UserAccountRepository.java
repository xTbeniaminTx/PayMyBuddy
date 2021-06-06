package fr.tolan.paymybuddy.daos;

import fr.tolan.paymybuddy.entities.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {

  UserAccount findByUserName(String username);

}
