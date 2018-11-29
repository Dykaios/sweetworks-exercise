package com.sweetworks.users.data.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sweetworks.users.data.models.Result;
import com.sweetworks.users.repositories.UsersRepository;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class UserViewModel
    extends ViewModel {

  private MutableLiveData<Result> search;
  private MutableLiveData<Result> moreData;
  private UsersRepository userRepository;

  public UserViewModel() {
    userRepository = new UsersRepository();
    if (search == null) {
      search = new MutableLiveData<>();
    }

    if (moreData == null) {
      moreData = new MutableLiveData<>();
    }

    userRepository.doSearch(search);
  }

  public LiveData<Result> getSearch() {
    return search;
  }

  public void getPage(int page) {
    userRepository.getPage(moreData, page);
  }

  public MutableLiveData<Result> getMoreData(){
    return moreData;
  }
}
