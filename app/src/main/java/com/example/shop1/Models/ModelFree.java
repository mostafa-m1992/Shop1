package com.example.shop1.Models;

public class ModelFree {

    int id;
    String image;
    String price;
    String title;
    String visit;
    String free;
    String label;

    public ModelFree(int id, String image, String price, String title, String visit, String free, String label) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.title = title;
        this.visit = visit;
        this.free = free;
        this.label = label;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
