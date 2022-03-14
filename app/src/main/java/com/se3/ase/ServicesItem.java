package com.se3.ase;

public class ServicesItem {
    private String id;
    private String title;
    private String desc;
    private String imageurl;

    public ServicesItem(String name, String desc, String imageurl, String id) {
        this.id = id;
        this.title = name;
        this.desc = desc;
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
