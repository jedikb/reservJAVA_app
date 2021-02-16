package com.example.reservjava_app.DTO;

import java.io.Serializable;

public class ProductDTO implements Serializable {
  private int product_code, product_business_code;
  private String product_name;
  private int product_price, product_price_deposit, product_stock;
  private  String product_image, product_info, product_time;

  public int getProduct_code() {
    return product_code;
  }

  public void setProduct_code(int product_code) {
    this.product_code = product_code;
  }

  public int getProduct_business_code() {
    return product_business_code;
  }

  public void setProduct_business_code(int product_business_code) {
    this.product_business_code = product_business_code;
  }

  public String getProduct_name() {
    return product_name;
  }

  public void setProduct_name(String product_name) {
    this.product_name = product_name;
  }

  public int getProduct_price() {
    return product_price;
  }

  public void setProduct_price(int product_price) {
    this.product_price = product_price;
  }

  public int getProduct_price_deposit() {
    return product_price_deposit;
  }

  public void setProduct_price_deposit(int product_price_deposit) {
    this.product_price_deposit = product_price_deposit;
  }

  public int getProduct_stock() {
    return product_stock;
  }

  public void setProduct_stock(int product_stock) {
    this.product_stock = product_stock;
  }

  public String getProduct_image() {
    return product_image;
  }

  public void setProduct_image(String product_image) {
    this.product_image = product_image;
  }

  public String getProduct_info() {
    return product_info;
  }

  public void setProduct_info(String product_info) {
    this.product_info = product_info;
  }

  public String getProduct_time() {
    return product_time;
  }

  public void setProduct_time(String product_time) {
    this.product_time = product_time;
  }

  public ProductDTO(int product_code, int product_business_code, String product_name, int product_price, int product_price_deposit, int product_stock, String product_image, String product_info, String product_time) {
    this.product_code = product_code;
    this.product_business_code = product_business_code;
    this.product_name = product_name;
    this.product_price = product_price;
    this.product_price_deposit = product_price_deposit;
    this.product_stock = product_stock;
    this.product_image = product_image;
    this.product_info = product_info;
    this.product_time = product_time;
  }


}
