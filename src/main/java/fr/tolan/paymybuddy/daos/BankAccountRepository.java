package fr.tolan.paymybuddy.daos;

import fr.tolan.paymybuddy.entities.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {

}
