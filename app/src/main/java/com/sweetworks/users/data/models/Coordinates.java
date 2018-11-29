package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class Coordinates
    implements Parcelable {
  private String latitude;
  private String longitude;

  protected Coordinates(Parcel in) {
    latitude = in.readString();
    longitude = in.readString();
  }

  public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
    @Override
    public Coordinates createFromParcel(Parcel in) {
      return new Coordinates(in);
    }

    @Override
    public Coordinates[] newArray(int size) {
      return new Coordinates[size];
    }
  };

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(latitude);
    parcel.writeString(longitude);
  }
}
