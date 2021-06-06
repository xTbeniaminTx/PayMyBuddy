package fr.tolan.paymybuddy.services;

import fr.tolan.paymybuddy.daos.TransactionRepository;
import fr.tolan.paymybuddy.entities.Transaction;
import fr.tolan.paymybuddy.entities.UserAccount;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

  public static final double COMISSION_PERCENTAGE = 0.005;


  @Autowired
  TransactionRepository transactionRepository;

  @PersistenceContext
  private EntityManager entityManager;


  public double fareCalculation(double amount) {
    return amount + (amount * COMISSION_PERCENTAGE);
  }


  public boolean verifySufficientBalance(UserAccount user, double amount) {
    return user.getBankAccount().getBalance() > fareCalculation(amount);
  }


  public boolean verifyIsAContact(UserAccount beneficiary, UserAccount transmitter) {
    for (UserAccount user : transmitter.getContacts()) {
      if (user.getUserName().equals(beneficiary.getUserName())) {
        return true;
      }
    }
    return false;
  }

  @Transactional
  public boolean transfer(UserAccount transmitter, UserAccount beneficiary, String description,
      double amount)
      throws Exception {
    if (amount > 0) {
      if (verifySufficientBalance(transmitter, amount)) {
        if (verifyIsAContact(beneficiary, transmitter)) {
          Transaction currentTransaction = new Transaction();
          currentTransaction.setAmount(amount);
          currentTransaction.setDescription(description);
          currentTransaction.setTransmitter(transmitter);
          currentTransaction.setUser(beneficiary);
          transactionRepository.save(currentTransaction);
          transmitter.getBankAccount()
              .setBalance((transmitter.getBankAccount().getBalance() - fareCalculation(amount)));
          beneficiary.getBankAccount()
              .setBalance(beneficiary.getBankAccount().getBalance() + amount);
          return true;
        } else {
          throw new Exception("You do not have this contact in your friends directory");
        }
      } else {
        throw new Exception("Your balance is not sufficient for this transfer");
      }
    } else {
      throw new Exception("Amount must be stricly superior to 0");
    }
  }

  public List<Transaction> getAllTransactionsByUser(UserAccount user) {
    List<Transaction> transactions1 = user.getTransactions();
    List<Transaction> transactions2 = user.getTransactionsAsTransmitter();
    List<Transaction> merged = new ArrayList<Transaction>(transactions1);
    merged.addAll(transactions2);

    return merged;
  }
}
