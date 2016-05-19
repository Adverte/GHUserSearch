package com.example.officepcdell.githubusersearch.interfaces;

import com.example.officepcdell.githubusersearch.classes.SearchRequest;
import com.example.officepcdell.githubusersearch.classes.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface githubServiceClass {
    @GET("search/users")
    Call<SearchResponse> requestUserList(@Query("q") String queryText);
}
