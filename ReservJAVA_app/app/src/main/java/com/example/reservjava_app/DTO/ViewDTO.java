package com.example.reservjava_app.DTO;

public class ViewDTO {
    int image_view ;
    String text_view ;

    public ViewDTO(int image_view, String text_view) {
        this.image_view = image_view;
        this.text_view = text_view;
    }

    public int getImage_view() {
        return image_view;
    }

    public void setImage_view(int image_view) {
        this.image_view = image_view;
    }

    public String getText_view() {
        return text_view;
    }

    public void setText_view(String text_view) {
        this.text_view = text_view;
    }
}
