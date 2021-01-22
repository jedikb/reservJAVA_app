package com.example.reservjava_app.DTO;


import java.io.Serializable;
import java.sql.Date;
/* 리사이클러뷰 테스트를 위해 임시로 만든 것임 삭제해야 함*/
public class ReviewDTO implements Serializable {
  private int booking_code, booking_member_code, booking_business_code, booking_product_code;
  private Date booking_date_reservation;
  private int booking_appraisal_star;
  private String booking_appraisal;

  public ReviewDTO(int booking_code, int booking_member_code, int booking_business_code, int booking_product_code, Date booking_date_reservation, int booking_appraisal_star, String booking_appraisal) {
    this.booking_code = booking_code;
    this.booking_member_code = booking_member_code;
    this.booking_business_code = booking_business_code;
    this.booking_product_code = booking_product_code;
    this.booking_date_reservation = booking_date_reservation;
    this.booking_appraisal_star = booking_appraisal_star;
    this.booking_appraisal = booking_appraisal;
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

  public Date getBooking_date_reservation() {
    return booking_date_reservation;
  }

  public void setBooking_date_reservation(Date booking_date_reservation) {
    this.booking_date_reservation = booking_date_reservation;
  }

  public int getBooking_appraisal_star() {
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
