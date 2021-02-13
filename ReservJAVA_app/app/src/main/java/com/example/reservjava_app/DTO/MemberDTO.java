package com.example.reservjava_app.DTO;

import java.io.Serializable;
import java.util.Date;

public class MemberDTO implements Serializable {
  private int member_code;
  private String member_id, member_pw;
  private int member_kind;
  private String member_name, member_nick, member_tel, member_email, member_addr, member_image;
  private Date member_date;
  private Double member_lat, member_lng;

  //전체 정보
  public MemberDTO(int member_code, String member_id, String member_pw, int member_kind, String member_name, String member_nick, String member_tel, String member_email, String member_addr, String member_image, Double member_lat, Double member_lng, Date member_date) {
    this.member_code = member_code;
    this.member_id = member_id;
    this.member_pw = member_pw;
    this.member_kind = member_kind;
    this.member_name = member_name;
    this.member_nick = member_nick;
    this.member_tel = member_tel;
    this.member_email = member_email;
    this.member_addr = member_addr;
    this.member_image = member_image;
    this.member_date = member_date;
    this.member_lat = member_lat;
    this.member_lng = member_lng;
  }

  public Double getMember_lat() {
    return member_lat;
  }

  public void setMember_lat(Double member_lat) {
    this.member_lat = member_lat;
  }

  public Double getMember_lng() {
    return member_lng;
  }

  public void setMember_lng(Double member_lng) {
    this.member_lng = member_lng;
  }

  public  MemberDTO() {}

  //정보 수정
/*  public MemberDTO(String member_id, String member_pw, String member_nick, String member_tel, String member_email, String member_image) {
    this.member_id = member_id;
    this.member_pw = member_pw;
    this.member_nick = member_nick;
    this.member_tel = member_tel;
    this.member_email = member_email;
    this.member_image = member_image;
  }*/

  //로그인(하다보니 자꾸 추가하게 되어,, 비밀번호를 제외하고 다 넣음)
  public MemberDTO(int member_code, String member_id, int member_kind, String member_name, String member_nick, String member_tel, String member_email, String member_addr, String member_image, Date member_date) {
    this.member_code = member_code;
    this.member_id = member_id;
    this.member_kind = member_kind;
    this.member_name = member_name;
    this.member_nick = member_nick;
    this.member_tel = member_tel;
    this.member_email = member_email;
    this.member_date = member_date;
    this.member_addr = member_addr;
    this.member_image = member_image;
  }

  public int getMember_code() {
    return member_code;
  }

  public void setMember_code(int member_code) {
    this.member_code = member_code;
  }

  public String getMember_id() {
    return member_id;
  }

  public void setMember_id(String member_id) {
    this.member_id = member_id;
  }

  public String getMember_pw() {
    return member_pw;
  }

  public void setMember_pw(String member_pw) {
    this.member_pw = member_pw;
  }

  public int getMember_kind() {
    return member_kind;
  }

  public void setMember_kind(int member_kind) {
    this.member_kind = member_kind;
  }

  public String getMember_name() {
    return member_name;
  }

  public void setMember_name(String member_name) {
    this.member_name = member_name;
  }

  public String getMember_nick() {
    return member_nick;
  }

  public void setMember_nick(String member_nick) {
    this.member_nick = member_nick;
  }

  public String getMember_tel() {
    return member_tel;
  }

  public void setMember_tel(String member_tel) {
    this.member_tel = member_tel;
  }

  public String getMember_email() {
    return member_email;
  }

  public void setMember_email(String member_email) {
    this.member_email = member_email;
  }

  public String getMember_addr() {
    return member_addr;
  }

  public void setMember_addr(String member_addr) { this.member_addr = member_addr; }

  public String getMember_image() {
    return member_image;
  }

  public void setMember_image(String member_image) {
    this.member_image = member_image;
  }

  public Date getMember_date() {
    return member_date;
  }

  public void setMember_date(Date member_date) {
    this.member_date = member_date;
  }
}
