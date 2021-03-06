package com.example.reservjava_app.DTO;


import java.io.Serializable;
import java.sql.Date;
/* 리사이클러뷰 테스트를 위해 임시로 만든 것임 삭제해야 함*/
public class ReviewDTO implements Serializable {
  private int booking_code, booking_kind, booking_member_code, booking_business_code, booking_product_code;
  //private Date booking_date_reservation;
  private String booking_date_reservation;
  private float booking_appraisal_star;
  private String booking_appraisal;
  // 리스트 뿌리기 위해 부킹 테이블 외에 연동해서 불러올 변수
  private String business_name, business_addr;
  private int business_category_code;
  //private      Date date ;
  public ReviewDTO(int booking_code, int booking_kind, int booking_member_code, int booking_business_code, int booking_product_code, String booking_date_reservation, float booking_appraisal_star
                  , String booking_appraisal, String business_name, int business_category_code, String business_addr) {
    this.booking_code = booking_code;
    this.booking_kind = booking_kind;
    this.booking_member_code = booking_member_code;
    this.booking_business_code = booking_business_code;
    this.booking_product_code = booking_product_code;
    this.booking_date_reservation = booking_date_reservation;
    this.booking_appraisal_star = booking_appraisal_star;
    this.booking_appraisal = booking_appraisal;
    this.business_name = business_name;
    this.business_category_code = business_category_code;
    this.business_addr = business_addr;
  }

  public ReviewDTO(int booking_code, int business_category_code, String business_name, float booking_appraisal_star, String booking_appraisal) {
    this.booking_code = booking_code;
    this.business_category_code = business_category_code;
    this.business_name = business_name;
    this.booking_appraisal_star = booking_appraisal_star;
    this.booking_appraisal = booking_appraisal;
  }

  //임시1
  public ReviewDTO(float booking_appraisal_star, String booking_appraisal) {
    this.booking_appraisal_star = booking_appraisal_star;
    this.booking_appraisal = booking_appraisal;
  }


  //임시2
  public ReviewDTO(float booking_appraisal_star, String booking_appraisal, String booking_date_reservation, String business_name) {
    this.booking_appraisal_star = booking_appraisal_star;
    this.booking_appraisal = booking_appraisal;
    this.booking_date_reservation = booking_date_reservation;
    this.business_name = business_name;
  }

  public String getBusiness_addr() {
    return business_addr;
  }

  public void setBusiness_addr(String business_addr) {
    this.business_addr = business_addr;
  }

  public void setBooking_appraisal_star(float booking_appraisal_star) {
    this.booking_appraisal_star = booking_appraisal_star;
  }

  public String getBusiness_name() {
    return business_name;
  }

  public void setBusiness_name(String business_name) {
    this.business_name = business_name;
  }

  public int getBusiness_category_code() {
    return business_category_code;
  }

  public void setBusiness_category_code(int business_category_code) {
    this.business_category_code = business_category_code;
  }

  public int getBooking_kind() {
    return booking_kind;
  }

  public void setBooking_kind(int booking_kind) {
    this.booking_kind = booking_kind;
  }

  public int getBooking_code() {
    return booking_code;
  }

  public void setBooking_code(int booking_code) {
    this.booking_code = booking_code;
  }

  public int getBooking_member_code() {
    return booking_member_code;
  }

  public void setBooking_member_code(int booking_member_code) {
    this.booking_member_code = booking_member_code;
  }

  public int getBooking_business_code() {
    return booking_business_code;
  }

  public void setBooking_business_code(int booking_business_code) {
    this.booking_business_code = booking_business_code;
  }

  public int getBooking_product_code() {
    return booking_product_code;
  }

  public void setBooking_product_code(int booking_product_code) {
    this.booking_product_code = booking_product_code;
  }

  public String getBooking_date_reservation() {
    return booking_date_reservation;
  }

  public void setBooking_date_reservation(String booking_date_reservation) {
    this.booking_date_reservation = booking_date_reservation;
  }

  public float getBooking_appraisal_star() {
    return booking_appraisal_star;
  }

  public void setBooking_appraisal_star(int booking_appraisal_star) {
    this.booking_appraisal_star = booking_appraisal_star;
  }

  public String getBooking_appraisal() {
    return booking_appraisal;
  }

  public void setBooking_appraisal(String booking_appraisal) {
    this.booking_appraisal = booking_appraisal;
  }
}
