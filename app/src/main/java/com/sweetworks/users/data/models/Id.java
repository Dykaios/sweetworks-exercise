package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class Id
    implements Parcelable {
  private String name;
  private String value;

  protected Id(Parcel in) {
    name = in.readString();
    value = in.readString();
  }

  public static final Creator<Id> CREATOR = new Creator<Id>() {
    @Override
    public Id createFromParcel(Parcel in) {
      return new Id(in);
    }

    @Override
    public Id[] newArray(int size) {
      return new Id[size];
    }
  };

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(name);
    parcel.writeString(value);
  }
}
