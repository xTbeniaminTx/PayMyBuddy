package fr.tolan.paymybuddy.daos;

import fr.tolan.paymybuddy.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
