package com.wangxin.springboot.model;


import java.util.Date;

public class PayOrderNotify {

  private Integer id;
  private String payOrderUuid;
  private String borrowOrderUuid;
  private String userUuid;
  private double borrowAmount;
  private Integer productId;
  private Date createdTime;

  @Override
  public String toString() {
    return "PayOrderNotify{" +
            "id=" + id +
            ", payOrderUuid='" + payOrderUuid + '\'' +
            ", borrowOrderUuid='" + borrowOrderUuid + '\'' +
            ", userUuid='" + userUuid + '\'' +
            ", borrowAmount=" + borrowAmount +
            ", productId=" + productId +
            ", createdTime=" + createdTime +
            '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getPayOrderUuid() {
    return payOrderUuid;
  }

  public void setPayOrderUuid(String payOrderUuid) {
    this.payOrderUuid = payOrderUuid;
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


  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

}
