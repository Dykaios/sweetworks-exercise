package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class Result
    implements Parcelable {
  List<User> results;

  public List<User> getResults() {
    return results;
  }

  public void setResults(List<User> results) {
    this.results = results;
  }

  public static Creator<Result> getCREATOR() {
    return CREATOR;
  }

  protected Result(Parcel in) {
    results = in.createTypedArrayList(User.CREATOR);
  }

  public static final Creator<Result> CREATOR = new Creator<Result>() {
    @Override
    public Result createFromParcel(Parcel in) {
      return new Result(in);
    }

    @Override
    public Result[] newArray(int size) {
      return new Result[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeTypedList(results);
  }
}
