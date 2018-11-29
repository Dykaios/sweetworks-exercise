package com.sweetworks.users.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.sweetworks.users.data.models.Result;
import com.sweetworks.users.webservices.Endpoints;
import com.sweetworks.users.webservices.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class UsersRepository {
  private static final String TAG = "UserRepository::";
  private Endpoints endpoints;

  public UsersRepository() {
    endpoints = Service.getEndpoints();
  }

  public void doSearch(MutableLiveData<Result> result) {
    // Hardcoded seed to always get the same result.
    Call<Result> call = endpoints.searchItem(50, "7da9630f00e8bee1");
    call.enqueue(new Callback<Result>() {

      @Override
      public void onResponse(Call<Result> call, Response<Result> response) {
        result.postValue(response.body());
      }

      @Override
      public void onFailure(Call<Result> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        result.postValue(null);
      }
    });
  }

  public void getPage(MutableLiveData<Result> result, int page) {
    Call<Result> call = endpoints.getMoreItems(50, "7da9630f00e8bee1", page);
    call.enqueue(new Callback<Result>() {
      @Override
      public void onResponse(Call<Result> call, Response<Result> response) {
        result.postValue(response.body());
      }

      @Override
      public void onFailure(Call<Result> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        result.postValue(null);
      }
    });
  }
}
