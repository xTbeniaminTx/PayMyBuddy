package fr.tolan.paymybuddy.daos;

import fr.tolan.paymybuddy.entities.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {

  UserAccount findByUserName(String username);

}
