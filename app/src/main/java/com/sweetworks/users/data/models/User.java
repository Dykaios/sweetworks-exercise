package com.sweetworks.users.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César Pardo on 25/11/2018.
 */
public class User
    implements Parcelable {
  private String gender;
  private Name name;
  private Location location;
  private String email;
  private Dob dob;
  private String phone;
  private String cell;
  private Id id;
  private Picture picture;
  private String nat;
  private boolean friend;

  protected User(Parcel in) {
    gender = in.readString();
    name = in.readParcelable(Name.class.getClassLoader());
    location = in.readParcelable(Location.class.getClassLoader());
    email = in.readString();
    dob = in.readParcelable(Dob.class.getClassLoader());
    phone = in.readString();
    cell = in.readString();
    id = in.readParcelable(Id.class.getClassLoader());
    picture = in.readParcelable(Picture.class.getClassLoader());
    nat = in.readString();
    friend = in.readByte() != 0;
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override
    public User createFromParcel(Parcel in) {
      return new User(in);
    }

    @Override
    public User[] newArray(int size) {
      return new User[size];
    }
  };

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Dob getDob() {
    return dob;
  }

  public void setDob(Dob dob) {
    this.dob = dob;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCell() {
    return cell;
  }

  public void setCell(String cell) {
    this.cell = cell;
  }

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  public Picture getPicture() {
    return picture;
  }

  public void setPicture(Picture picture) {
    this.picture = picture;
  }

  public String getNat() {
    return nat;
  }

  public void setNat(String nat) {
    this.nat = nat;
  }

  public boolean isFriend() {
    return friend;
  }

  public void setFriend(boolean friend) {
    this.friend = friend;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(gender);
    parcel.writeParcelable(name, i);
    parcel.writeParcelable(location, i);
    parcel.writeString(email);
    parcel.writeParcelable(dob, i);
    parcel.writeString(phone);
    parcel.writeString(cell);
    parcel.writeParcelable(id, i);
    parcel.writeParcelable(picture, i);
    parcel.writeString(nat);
    parcel.writeByte((byte) (friend ? 1 : 0));
  }
}