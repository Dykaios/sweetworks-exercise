package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class Picture
    implements Parcelable {
  private String large;
  private String medium;
  private String thumbnail;

  protected Picture(Parcel in) {
    large = in.readString();
    medium = in.readString();
    thumbnail = in.readString();
  }

  public static final Creator<Picture> CREATOR = new Creator<Picture>() {
    @Override
    public Picture createFromParcel(Parcel in) {
      return new Picture(in);
    }

    @Override
    public Picture[] newArray(int size) {
      return new Picture[size];
    }
  };

  public String getLarge() {
    return large;
  }

  public void setLarge(String large) {
    this.large = large;
  }

  public String getMedium() {
    return medium;
  }

  public void setMedium(String medium) {
    this.medium = medium;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(large);
    parcel.writeString(medium);
    parcel.writeString(thumbnail);
  }
}
