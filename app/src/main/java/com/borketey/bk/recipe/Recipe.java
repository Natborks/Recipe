package com.borketey.bk.recipe;

import com.google.firebase.database.Exclude;


public class Recipe  {

    private String username;
    private String description;
    private String title;
    private String method;
    private String photo_url;
    private String likes;

    public Recipe() {{
    }
    }

    //constructor
    public Recipe (String user, String description, String title,
                   String method, String photo_url,String likes) {

        this.username = user;
        this.description = description;
        this.title = title;
        this.method = method;
        this.photo_url = photo_url;
        this.likes = likes;
    }

    /*****
     Setters and getters for Recipe POJO
     ****/
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
