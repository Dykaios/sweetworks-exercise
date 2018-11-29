package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class Dob
    implements Parcelable {
  private String date;
  private String age;

  protected Dob(Parcel in) {
    date = in.readString();
    age = in.readString();
  }

  public static final Creator<Dob> CREATOR = new Creator<Dob>() {
    @Override
    public Dob createFromParcel(Parcel in) {
      return new Dob(in);
    }

    @Override
    public Dob[] newArray(int size) {
      return new Dob[size];
    }
  };

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(date);
    parcel.writeString(age);
  }
}
