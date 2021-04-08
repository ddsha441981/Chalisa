package com.codewithcup.chalisa.Model;

public class SaveChildModel {

    String name;
    String image;
    String count;
    String description;

    public SaveChildModel() {
    }

    public SaveChildModel(String name, String image, String count, String description) {
        this.name = name;
        this.image = image;
        this.count = count;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
