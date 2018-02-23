package com.gauravbhola.androidboilerplate.model;


import com.google.gson.annotations.SerializedName;

public class Owner {
    private String login;
    private int id;
    @SerializedName("avatar_url")
    private String avatarUrl;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
