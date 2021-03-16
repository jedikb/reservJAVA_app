package com.example.reservjava_app.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryImageDTO implements Serializable {
  private int category_img, category_code ; String back_color;

  public CategoryImageDTO(int category_img, int category_code , String back_color) {
    this.category_img = category_img;
    this.category_code = category_code;
    this.back_color = back_color;
  }

  public String getBack_color() {
    return back_color;
  }

  public void setBack_color(String back_color) {
    this.back_color = back_color;
  }

  public int getCategory_img() {
    return category_img;
  }

  public void setCategory_img(int category_img) {
    this.category_img = category_img;
  }

  public int getCategory_code() {
    return category_code;
  }

  public void setCategory_code(int category_code) {
    this.category_code = category_code;
  }
}
