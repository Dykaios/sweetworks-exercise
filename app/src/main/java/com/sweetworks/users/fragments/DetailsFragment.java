package com.sweetworks.users.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.sweetworks.users.R;
import com.sweetworks.users.data.models.User;

import java.util.Objects;
import java.util.Set;

import static com.sweetworks.users.Constants.APP_NAME;
import static com.sweetworks.users.Constants.FIRENDS_LIST;

/**
 * Created by César Pardo on 27/11/2018.
 */
public class DetailsFragment extends Fragment {

  private User user;
  private TextInputEditText fistName;
  private TextInputEditText lastName;
  private TextInputEditText email;
  private TextInputEditText phone;
  private TextInputEditText cellphone;
  private TextInputEditText city;
  private TextInputEditText address;
  private TextInputEditText postcode;
  private TextInputEditText nat;
  private ImageView addFriend;

  public static DetailsFragment newInstance(User user) {
    DetailsFragment fragment = new DetailsFragment();
    Bundle args = new Bundle();
    args.putParcelable("USER", user);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      user = getArguments().getParcelable("USER");
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ImageView photo = view.findViewById(R.id.user_photo);
    fistName = view.findViewById(R.id.name_text_input_edit_layout);
    lastName = view.findViewById(R.id.last_name_text_input_edit_layout);
    email = view.findViewById(R.id.email_text_input_edit_layout);
    phone = view.findViewById(R.id.phone_text_input_edit_layout);
    cellphone = view.findViewById(R.id.cell_text_input_edit_layout);
    city = view.findViewById(R.id.city_text_input_edit_layout);
    address = view.findViewById(R.id.address_text_input_edit_layout);
    postcode = view.findViewById(R.id.postal_code_text_input_edit_layout);
    nat = view.findViewById(R.id.nat_text_input_edit_layout);
    FloatingActionButton fabAddContact = view.findViewById(R.id.add_contact);
    addFriend = view.findViewById(R.id.add_friend);

    Picasso
        .get()
        .load(user.getPicture().getLarge())
        .resize(500, 500)
        .centerCrop()
        .into(photo);

    loadUser();

    fabAddContact.setOnClickListener(view1 -> {
      Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
      intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
      intent.putExtra(ContactsContract.Intents.Insert.EMAIL, user.getEmail())
          .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
          .putExtra(ContactsContract.Intents.Insert.PHONE, user.getPhone())
          .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
          .putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, user.getCell())
          .putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
          .putExtra(ContactsContract.Intents.Insert.NAME, user.getName().getFirst() + " " + user.getName().getLast());
      startActivity(intent);
    });

    addFriend.setOnClickListener(view12 -> {
      if (!user.isFriend()) {
        user.setFriend(true);
        addFriend.setImageResource(R.drawable.ic_heart_enable);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        //Know Issue: A better solution for this should be use a database like ROOM.
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        Set<String> friends = sharedPreferences.getStringSet(FIRENDS_LIST, new ArraySet<>());
        if (friends.add(json)) {
          sharedPreferences.edit().putStringSet(FIRENDS_LIST, friends)
              .putInt("COUNT", friends.size()).apply();
        } else {
          Toast.makeText(getContext(), "This user its already your friend.", Toast.LENGTH_LONG).show();
        }
      }
    });
  }

  public void loadUser() {
    fistName.setText(user.getName().getFirst());
    lastName.setText(user.getName().getLast());
    email.setText(user.getEmail());
    phone.setText(user.getPhone());
    cellphone.setText(user.getCell());
    city.setText(user.getLocation().getCity());
    address.setText(user.getLocation().getStreet());
    postcode.setText(user.getLocation().getPostcode());
    nat.setText(user.getNat());
    if (user.isFriend()) {
      addFriend.setImageResource(R.drawable.ic_heart_enable);
    }
  }
}
