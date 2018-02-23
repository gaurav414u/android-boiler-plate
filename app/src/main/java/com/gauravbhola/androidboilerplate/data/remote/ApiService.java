package com.gauravbhola.androidboilerplate.data.remote;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search/repositories")
    Call<RepoSearchResponse> searchRepos(@Query("q") String query);
}
