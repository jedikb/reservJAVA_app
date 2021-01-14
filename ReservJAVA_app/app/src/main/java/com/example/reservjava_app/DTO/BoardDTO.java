package com.example.reservjava_app.DTO;

import java.sql.Date;

public class BoardDTO {
  private int board_code, board_status, board_group_code, board_member_code;
  private String board_title, board_content;
  private Date board_write_date, board_update_date;
  private int board_readcount;

  public BoardDTO(int board_code, int board_status, int board_group_code, int board_member_code, String board_title, String board_content, Date board_write_date, Date board_update_date, int board_readcount) {
    this.board_code = board_code;
    this.board_status = board_status;
    this.board_group_code = board_group_code;
    this.board_member_code = board_member_code;
    this.board_title = board_title;
    this.board_content = board_content;
    this.board_write_date = board_write_date;
    this.board_update_date = board_update_date;
    this.board_readcount = board_readcount;
  }

  public int getBoard_code() {
    return board_code;
  }

  public void setBoard_code(int board_code) {
    this.board_code = board_code;
  }

  public int getBoard_status() {
    return board_status;
  }

  public void setBoard_status(int board_status) {
    this.board_status = board_status;
  }

  public int getBoard_group_code() {
    return board_group_code;
  }

  public void setBoard_group_code(int board_group_code) {
    this.board_group_code = board_group_code;
  }

  public int getBoard_member_code() {
    return board_member_code;
  }

  public void setBoard_member_code(int board_member_code) {
    this.board_member_code = board_member_code;
  }

  public String getBoard_title() {
    return board_title;
  }

  public void setBoard_title(String board_title) {
    this.board_title = board_title;
  }

  public String getBoard_content() {
    return board_content;
  }

  public void setBoard_content(String board_content) {
    this.board_content = board_content;
  }

  public Date getBoard_write_date() {
    return board_write_date;
  }

  public void setBoard_write_date(Date board_write_date) {
    this.board_write_date = board_write_date;
  }

  public Date getBoard_update_date() {
    return board_update_date;
  }

  public void setBoard_update_date(Date board_update_date) {
    this.board_update_date = board_update_date;
  }

  public int getBoard_readcount() {
    return board_readcount;
  }

  public void setBoard_readcount(int board_readcount) {
    this.board_readcount = board_readcount;
  }
}
