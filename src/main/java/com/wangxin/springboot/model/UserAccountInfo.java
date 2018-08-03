package com.wangxin.springboot.model;


public class UserAccountInfo {

  private Integer id;
  private String userUuid;
  private double totalAssets;

  @Override
  public String toString() {
    return "UserAccountInfo{" +
            "id=" + id +
            ", userUuid='" + userUuid + '\'' +
            ", totalAssets=" + totalAssets +
            '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getUserUuid() {
    return userUuid;
  }

  public void setUserUuid(String userUuid) {
    this.userUuid = userUuid;
  }


  public double getTotalAssets() {
    return totalAssets;
  }

  public void setTotalAssets(double totalAssets) {
    this.totalAssets = totalAssets;
  }

}
