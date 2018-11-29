package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César Pardo on 25/11/2018.
 */
public class Name
    implements Parcelable {
  private String title;
  private String first;
  private String last;

  protected Name(Parcel in) {
    title = in.readString();
    first = in.readString();
    last = in.readString();
  }

  public static final Creator<Name> CREATOR = new Creator<Name>() {
    @Override
    public Name createFromParcel(Parcel in) {
      return new Name(in);
    }

    @Override
    public Name[] newArray(int size) {
      return new Name[size];
    }
  };

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(title);
    parcel.writeString(first);
    parcel.writeString(last);
  }
}
