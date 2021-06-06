package fr.tolan.paymybuddy.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserAccount {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String userName;

  private String email;

  private String password;

  private String role = "USER";

  @Column(name = "status")
  private boolean enabled = true;

  @ManyToMany
  @JoinTable(
      name="contacts",
      joinColumns = { @JoinColumn(name="first_user") },
      inverseJoinColumns = { @JoinColumn(name = "second_user") }
  )
  private Set<UserAccount> contacts;

  @ManyToMany
  @JoinTable(
      name="contacts",
      joinColumns = { @JoinColumn(name="second_user") },
      inverseJoinColumns = { @JoinColumn(name = "first_user") }
  )
  private Set<UserAccount> contactOf;

  public UserAccount() {
  }

  public UserAccount(Integer id, String userName, String email, String password, String role,
      boolean enabled) {
    this.id = id;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.role = role;
    this.enabled = enabled;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
