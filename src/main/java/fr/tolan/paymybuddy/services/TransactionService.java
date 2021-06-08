package fr.tolan.paymybuddy.services;


import fr.tolan.paymybuddy.entities.UserAccount;


public interface TransactionService {

  public boolean transfer(UserAccount transmitter, UserAccount beneficiary, String description,
      double amount) throws Exception;

  public double fareCalculation(double amount);

  public boolean verifySufficientBalance(UserAccount user, double amount);

  public boolean verifyIsAContact(UserAccount beneficiary, UserAccount transmitter);

  public UserAccount addMoney(double account) throws Exception;

}
