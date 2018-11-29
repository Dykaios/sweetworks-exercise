package com.sweetworks.users.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.sweetworks.users.R;
import com.sweetworks.users.data.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by César Pardo on 26/11/2018.
 */
public class UsersAdapter
    extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

  private List<User> users;
  private OnItemClickListener listener;

  public void setOnClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  public void setItems(List<User> users) {
    this.users = users;
    notifyDataSetChanged();
  }

  public void addItems(List<User> users) {
    if (this.users == null) {
      this.users = new ArrayList<>();
    }
    this.users.addAll(users);
    notifyDataSetChanged();
  }

  public void clear() {
    users = new ArrayList<>();
  }

  public User getUser(String userName) {
    String aux;
    for (User user : users) {
      aux = user.getName().getFirst() + " " + user.getName().getLast();
      if (aux.equals(userName)) {
        return user;
      }
    }
    return null;
  }

  @NonNull
  @Override
  public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_line, parent, false);
    return new UserHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull UserHolder holder, int position) {
    // Set the values to the clientModel view holder
    User user = users.get(position);
    Picasso
        .get()
        .load(user.getPicture().getMedium())
        .resize(92, 92)
        .centerCrop()
        .into(holder.userThumbnail);
  }

  @Override
  public int getItemCount() {
    if (users == null) {
      users = new ArrayList<>();
    }
    return users.size();
  }

  class UserHolder
      extends RecyclerView.ViewHolder {
    ImageView userThumbnail;

    UserHolder(View itemView) {
      super(itemView);
      userThumbnail = itemView.findViewById(R.id.user_thumbnail);

      //Notified that the item its clicked.
      itemView.setOnClickListener(view -> {
        if (listener != null) {
          listener.onItemClick(view, users.get(getAdapterPosition()));
        }
      });
    }
  }

  public interface OnItemClickListener {
    void onItemClick(View view, User item);
  }
}
