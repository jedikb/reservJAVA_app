package com.example.reservjava_app.DTO;

import java.io.Serializable;

public class BusinessDTO implements Serializable {
  private int business_code;
  private String business_name;
  private int business_member_code, business_category_code1, business_category_code2;
  private  String business_addr, business_tel, business_image, business_info;
  private double business_star_avg;

  public BusinessDTO(){}

  public BusinessDTO(int business_code, String business_name, int business_member_code, int business_category_code1, int business_category_code2, String business_addr, String business_tel, String business_image, String business_info, double business_star_avg) {
    this.business_code = business_code;
    this.business_name = business_name;
    this.business_member_code = business_member_code;
    this.business_category_code1 = business_category_code1;
    this.business_category_code2 = business_category_code2;
    this.business_addr = business_addr;
    this.business_tel = business_tel;
    this.business_image = business_image;
    this.business_info = business_info;
    this.business_star_avg = business_star_avg;
  }

  //RecyclerView 테스트용
  public BusinessDTO(String business_name, String business_addr) {
    this.business_name = business_name;
    this.business_addr = business_addr;
  }

  //SearchBusiness
  public BusinessDTO(String business_name, String business_addr, double business_star_avg) {
    this.business_name = business_name;
    this.business_addr = business_addr;
    this.business_star_avg = business_star_avg;
  }

  public int getBusiness_code() {
    return business_code;
  }

  public void setBusiness_code(int business_code) {
    this.business_code = business_code;
  }

  public String getBusiness_name() {
    return business_name;
  }

  public void setBusiness_name(String business_name) {
    this.business_name = business_name;
  }

  public int getBusiness_member_code() {
    return business_member_code;
  }

  public void setBusiness_member_code(int business_member_code) {
    this.business_member_code = business_member_code;
  }

  public int getBusiness_category_code1() {
    return business_category_code1;
  }

  public void setBusiness_category_code1(int business_category_code1) {
    this.business_category_code1 = business_category_code1;
  }

  public int getBusiness_category_code2() {
    return business_category_code2;
  }

  public void setBusiness_category_code2(int business_category_code2) {
    this.business_category_code2 = business_category_code2;
  }

  public String getBusiness_addr() {
    return business_addr;
  }

  public void setBusiness_addr(String business_addr) {
    this.business_addr = business_addr;
  }

  public String getBusiness_tel() {
    return business_tel;
  }

  public void setBusiness_tel(String business_tel) {
    this.business_tel = business_tel;
  }

  public String getBusiness_image() {
    return business_image;
  }

  public void setBusiness_image(String business_image) {
    this.business_image = business_image;
  }

  public String getBusiness_info() {
    return business_info;
  }

  public void setBusiness_info(String business_info) {
    this.business_info = business_info;
  }

  public double getBusiness_star_avg() {
    return business_star_avg;
  }

  public void setBusiness_star_avg(double business_star_avg) {
    this.business_star_avg = business_star_avg;
  }
}
