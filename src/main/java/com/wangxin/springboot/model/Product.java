package com.wangxin.springboot.model;


public class Product {

  private Integer id;
  private String name;
  private String countType;
  private double minimumAmount;
  private double interestRate;
  private String annotation;
  private Integer personJoined;
  private Integer investmentHorizon;
  private Integer paybackMethod;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getCountType() {
    return countType;
  }

  public void setCountType(String countType) {
    this.countType = countType;
  }


  public double getMinimumAmount() {
    return minimumAmount;
  }

  public void setMinimumAmount(double minimumAmount) {
    this.minimumAmount = minimumAmount;
  }


  public double getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(double interestRate) {
    this.interestRate = interestRate;
  }


  public String getAnnotation() {
    return annotation;
  }

  public void setAnnotation(String annotation) {
    this.annotation = annotation;
  }


  public Integer getPersonJoined() {
    return personJoined;
  }

  public void setPersonJoined(Integer personJoined) {
    this.personJoined = personJoined;
  }


  public Integer getInvestmentHorizon() {
    return investmentHorizon;
  }

  public void setInvestmentHorizon(Integer investmentHorizon) {
    this.investmentHorizon = investmentHorizon;
  }


  public Integer getPaybackMethod() {
    return paybackMethod;
  }

  public void setPaybackMethod(Integer paybackMethod) {
    this.paybackMethod = paybackMethod;
  }

}
