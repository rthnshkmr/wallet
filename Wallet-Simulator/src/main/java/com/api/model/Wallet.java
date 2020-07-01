package com.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Wallet {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String name;
  private String address;
  private Integer noOfCoins;
  private double balance;
  private String coinType;
  
  
  public Wallet() {}

public Wallet(String name, String address, Integer coins, double balance, String type) {
	this.name = name;
	this.address = address;
	this.noOfCoins = coins;
	this.balance = balance;
	this.coinType = type;
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @return the address
 */
public String getAddress() {
	return address;
}

public Long getId() {
    return id;
  }


/**
 * @param id the id to set
 */
public void setId(Long id) {
	this.id = id;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * @param address the address to set
 */
public void setAddress(String address) {
	this.address = address;
}

/**
 * @return the balance
 */
public double getBalance() {
	return balance;
}

/**
 * @param balance the balance to set
 */
public void setBalance(double balance) {
	this.balance = balance;
}

/**
 * @return the noOfCoins
 */
public Integer getNoOfCoins() {
	return noOfCoins;
}

/**
 * @param noOfCoins the noOfCoins to set
 */
public void setNoOfCoins(Integer noOfCoins) {
	this.noOfCoins = noOfCoins;
}

/**
 * @return the coinType
 */
public String getCoinType() {
	return coinType;
}

/**
 * @param coinType the coinType to set
 */
public void setCoinType(String coinType) {
	this.coinType = coinType;
}

@Override
public String toString() {
	return "Wallet [id=" + id + ", name=" + name + ", address=" + address + ", noOfCoins=" + noOfCoins + ", balance=$"
			+ balance + ", coinType=" + coinType + "]";
}



}