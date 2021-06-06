package fr.tolan.paymybuddy.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class BankAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer idBankAccount;

  private double balance;

  @OneToOne(mappedBy = "bankAccount")
  @JoinColumn(name = "user_id")
  private UserAccount user;

  public BankAccount() {
  }

  public BankAccount(Integer idBankAccount, double balance,
      UserAccount user) {
    this.idBankAccount = idBankAccount;
    this.balance = balance;
    this.user = user;
  }

  public Integer getIdBankAccount() {
    return idBankAccount;
  }

  public void setIdBankAccount(Integer idBankAccount) {
    this.idBankAccount = idBankAccount;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public UserAccount getUser() {
    return user;
  }

  public void setUser(UserAccount user) {
    this.user = user;
  }
}

