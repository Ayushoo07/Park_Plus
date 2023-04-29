package com.driver.model;

import javax.persistence.*;

@Entity
public class Reservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int numberOfHours;
    public Reservation(int id, int numberOfHours) {
    this.id=id;
    this.numberOfHours=numberOfHours;
    }

  public Reservation() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getNumberOfHours() {
    return numberOfHours;
  }

  public void setNumberOfHours(int numberOfHours) {
    this.numberOfHours = numberOfHours;
  }

  public Spot getSpot() {
    return spot;
  }

  public void setSpot(Spot spot) {
    this.spot = spot;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @ManyToOne @JoinColumn Spot spot;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    @JoinColumn
    Payment payment;

    @ManyToOne
    @JoinColumn
    User user;


}
