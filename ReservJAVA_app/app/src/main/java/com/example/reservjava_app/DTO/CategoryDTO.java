package com.example.reservjava_app.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryDTO implements Serializable {
  private int category_code, category_parent_code;
  private String category_name, category_info;
  private int cnt;
  public ArrayList<Category_SubDTO> Items;
  public int getCnt() {
    return cnt;
  }

  public void setCnt(int cnt) {
    this.cnt = cnt;
  }


  public CategoryDTO(int category_code, int category_parent_code, String category_name, String category_info, int cnt) {
    this.category_code = category_code;
    this.category_parent_code = category_parent_code;
    this.category_name = category_name;
    this.category_info = category_info;
    this.cnt = cnt;
  }


  public CategoryDTO(int category_code, int category_parent_code, String category_name, String category_info) {
    this.category_code = category_code;
    this.category_parent_code = category_parent_code;
    this.category_name = category_name;
    this.category_info = category_info;
  }

  public ArrayList<Category_SubDTO> getItems() {
    return Items;
  }

  public void setItems(ArrayList<Category_SubDTO> items) {
    Items = items;
  }

  public int getCategory_code() {
    return category_code;
  }

  public void setCategory_code(int category_code) {
    this.category_code = category_code;
  }

  public int getCategory_parent_code() {
    return category_parent_code;
  }

  public void setCategory_parent_code(int category_parent_code) {
    this.category_parent_code = category_parent_code;
  }

  public String getCategory_name() {
    return category_name;
  }

  public void setCategory_name(String category_name) {
    this.category_name = category_name;
  }

  public String getCategory_info() {
    return category_info;
  }

  public void setCategory_info(String category_info) {
    this.category_info = category_info;
  }
}
