package com.example.officepcdell.githubusersearch.interfaces;

import com.example.officepcdell.githubusersearch.classes.searchRequest;
import com.example.officepcdell.githubusersearch.classes.searchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface githubServiceClass {
    @GET("search/users")
    Call<searchResponse> requestUserList(@Query("q") searchRequest searchTerms);
}
