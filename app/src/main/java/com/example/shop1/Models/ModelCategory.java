package com.example.shop1.Models;

public class ModelCategory {

    int id;
    String image;
    String titleCategory;

    public ModelCategory(int id, String image, String titleCategory) {
        this.id = id;
        this.image = image;
        this.titleCategory = titleCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitleCategory() {
        return titleCategory;
    }

    public void setTitleCategory(String titleCategory) {
        this.titleCategory = titleCategory;
    }
}
