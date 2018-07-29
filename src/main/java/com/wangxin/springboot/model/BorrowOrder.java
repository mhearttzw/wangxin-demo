package com.wangxin.springboot.model;


import java.util.Date;

public class BorrowOrder {

  private Integer id;
  private String borrowOrderUuid;
  private String userUuid;
  private double borrowAmount;
  private Integer productId;
  private Integer state;
  private Date createdTime;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getBorrowOrderUuid() {
    return borrowOrderUuid;
  }

  public void setBorrowOrderUuid(String borrowOrderUuid) {
    this.borrowOrderUuid = borrowOrderUuid;
  }


  public String getUserUuid() {
    return userUuid;
  }

  public void setUserUuid(String userUuid) {
    this.userUuid = userUuid;
  }


  public double getBorrowAmount() {
    return borrowAmount;
  }

  public void setBorrowAmount(double borrowAmount) {
    this.borrowAmount = borrowAmount;
  }


  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }


  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }


  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

}
