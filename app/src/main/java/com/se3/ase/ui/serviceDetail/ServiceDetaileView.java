package com.se3.ase.ui.serviceDetail;

import com.se3.ase.data.model.CategoryModel;

import java.util.List;

public class ServiceDetaileView {
    private List<CategoryModel> categories;
    private String image;
    private String desciption;
    private String name;

    public ServiceDetaileView(List<CategoryModel> categories, String image, String desciption, String name) {
        this.categories = categories;
        this.image = image;
        this.desciption = desciption;
        this.name = name;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public String getImage() {
        return image;
    }

    public String getDesciption() {
        return desciption;
    }

    public String getName() {
        return name;
    }
}
