package com.codewithcup.chalisa.Model;

public class MainModel {

    private String title;
    private String thumbnail;
    private String name;
    private String image;
    private String description;

    public MainModel() {
    }

    public MainModel(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }


    public MainModel(String title, String thumbnail, String name, String image, String description) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
