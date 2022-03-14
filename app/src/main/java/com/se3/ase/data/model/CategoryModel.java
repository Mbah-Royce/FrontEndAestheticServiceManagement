package com.se3.ase.data.model;

public class CategoryModel {
    String price;
    String name;
    String desc;
    String duration;
    String id;

    public CategoryModel(String price, String name, String desc, String duration, String id) {
        this.price = price;
        this.name = name;
        this.desc = desc;
        this.duration = duration;
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
