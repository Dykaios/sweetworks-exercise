package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class TimeZone
    implements Parcelable {
  private String offset;
  private String description;

  protected TimeZone(Parcel in) {
    offset = in.readString();
    description = in.readString();
  }

  public static final Creator<TimeZone> CREATOR = new Creator<TimeZone>() {
    @Override
    public TimeZone createFromParcel(Parcel in) {
      return new TimeZone(in);
    }

    @Override
    public TimeZone[] newArray(int size) {
      return new TimeZone[size];
    }
  };

  public String getOffset() {
    return offset;
  }

  public void setOffset(String offset) {
    this.offset = offset;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(offset);
    parcel.writeString(description);
  }
}
