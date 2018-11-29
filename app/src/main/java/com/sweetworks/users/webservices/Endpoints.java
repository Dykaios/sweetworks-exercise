package com.sweetworks.users.webservices;

import com.sweetworks.users.data.models.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by César Pardo on 25/11/2018.
 */
public interface Endpoints {
  //Use a seed to always get the same users.
  @GET("api/")
  Call<Result> searchItem(@Query("results") int results, @Query("seed") String seed);

  @GET("api/")
  Call<Result> getMoreItems(@Query("results") int results, @Query("seed") String seed, @Query("page") int page);
}
