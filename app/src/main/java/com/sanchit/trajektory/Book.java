package com.sanchit.trajektory;

public class Book {

    private String Title;
    private String Category ;
    private String Description ;
    private int Thumbnail ;
    private String Url;

    public Book() {
    }

    public Book(String title, String category, String description, int thumbnail , String url) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Url = url;

    }


    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public String getUrl() {
        return Url;
    }


    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
