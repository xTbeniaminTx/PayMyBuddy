package fr.tolan.paymybuddy.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer idTransaction;

  private double amount;

  private String description;

  @ManyToOne
  @JoinColumn(name = "receiver_id", nullable = false)
  private UserAccount user;

  @ManyToOne
  @JoinColumn(name = "transmitter_id", nullable = false)
  private UserAccount transmitter;

  public Transaction() {
  }

  public Transaction(Integer idTransaction, double amount, String description,
      UserAccount user, UserAccount transmitter) {
    this.idTransaction = idTransaction;
    this.amount = amount;
    this.description = description;
    this.user = user;
    this.transmitter = transmitter;
  }

  public Integer getIdTransaction() {
    return idTransaction;
  }

  public void setIdTransaction(Integer idTransaction) {
    this.idTransaction = idTransaction;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UserAccount getUser() {
    return user;
  }

  public void setUser(UserAccount user) {
    this.user = user;
  }

  public UserAccount getTransmitter() {
    return transmitter;
  }

  public void setTransmitter(UserAccount transmitter) {
    this.transmitter = transmitter;
  }
}
