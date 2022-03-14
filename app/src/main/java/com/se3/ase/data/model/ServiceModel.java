package com.se3.ase.data.model;

public class ServiceModel {
    private String id;
    private String title;
    private String desc;
    private String imageurl;

    public ServiceModel(String id, String title, String desc, String imageurl) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
