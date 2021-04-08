package com.codewithcup.chalisa.Model;

public class ModelClass {

    private String title;
    //private String category;
    private String description;
    private String thumbnail;

    public ModelClass(String title, String thumbnail,String description) {
        this.title = title;
        //this.category = category;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public ModelClass() {

    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
*/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
