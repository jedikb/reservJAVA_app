package com.example.reservjava_app.DTO;

import java.util.List;

public class SampleModel {

    public String mydata;

    public List<Category_SubDTO> Items;


    public void setData(String mydata){

        this.mydata = mydata;

    }



    public void setItems(List<Category_SubDTO> items){

        this.Items = items;

    }



    public String getData(){

        return this.mydata;

    }



    public List<Category_SubDTO> getItems(){

        return this.Items;

    }

}
