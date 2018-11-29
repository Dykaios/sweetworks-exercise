package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César Pardo on 25/11/2018.
 */
public class Location
    implements Parcelable {
  private String street;
  private String city;
  private String state;
  private String postcode;
  private Coordinates coordinates;
  private TimeZone timezone;

  protected Location(Parcel in) {
    street = in.readString();
    city = in.readString();
    state = in.readString();
    postcode = in.readString();
  }

  public static final Creator<Location> CREATOR = new Creator<Location>() {
    @Override
    public Location createFromParcel(Parcel in) {
      return new Location(in);
    }

    @Override
    public Location[] newArray(int size) {
      return new Location[size];
    }
  };

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public TimeZone getTimezone() {
    return timezone;
  }

  public void setTimezone(TimeZone timezone) {
    this.timezone = timezone;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(street);
    parcel.writeString(city);
    parcel.writeString(state);
    parcel.writeString(postcode);
  }
}
