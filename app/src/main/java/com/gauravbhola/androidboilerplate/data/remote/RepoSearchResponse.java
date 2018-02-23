package com.gauravbhola.androidboilerplate.data.remote;


import com.google.gson.annotations.SerializedName;

import com.gauravbhola.androidboilerplate.model.Repo;

import java.util.List;

public class RepoSearchResponse {
    @SerializedName("total_count")
    private int total;
    List<Repo> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }
}
