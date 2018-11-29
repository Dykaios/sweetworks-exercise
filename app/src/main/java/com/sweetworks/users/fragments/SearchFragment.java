package com.sweetworks.users.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.sweetworks.users.R;
import com.sweetworks.users.adapters.UsersAdapter;
import com.sweetworks.users.data.models.Result;
import com.sweetworks.users.data.models.User;
import com.sweetworks.users.data.view_models.UserViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.sweetworks.users.Constants.APP_NAME;
import static com.sweetworks.users.Constants.FIRENDS_LIST;

/**
 * Created by César Pardo on 25/11/2018.
 */
public class SearchFragment
    extends Fragment
    implements UsersAdapter.OnItemClickListener {

  private static final int SEARCHING = 0;
  private static final int RESULT = 1;
  private static final int NO_SEARCH = 0;
  private static final int PROGRESS = 1;
  private static final int ITEM_LIST = 0;
  private static final int EMPTY_LIST = 1;

  private RecyclerView friendsRV;
  private AutoCompleteTextView searchET;
  private ViewSwitcher searchVS;
  private ViewSwitcher searchingVS;
  private ViewSwitcher resultVS;

  private UsersAdapter adapter;
  private UsersAdapter friendsAdapter;
  private ArrayAdapter<String> adapterNames;
  private GridLayoutManager glm;
  private int scrollPosition = 0;
  private boolean gettingPage = false;
  private int page = 1;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_search, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    searchET = view.findViewById(R.id.search_text_input_edit_layout);
    searchVS = view.findViewById(R.id.search_view_switcher);
    searchingVS = view.findViewById(R.id.searching_view_switcher);
    resultVS = view.findViewById(R.id.result_view_switcher);

    //Use a LiveData to persist data against rotation screen.
    UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    searchingVS.setDisplayedChild(PROGRESS);
    userViewModel.getSearch().observe(Objects.requireNonNull(this), search -> {
      // update UI
      if (search == null) {
        Snackbar.make(view, R.string.error_getting_search, Snackbar.LENGTH_LONG).show();
        searchVS.setDisplayedChild(SEARCHING);
        searchingVS.setDisplayedChild(NO_SEARCH);
      } else {
        glm.scrollToPosition(scrollPosition);
        searchVS.setDisplayedChild(RESULT);
        searchingVS.setDisplayedChild(NO_SEARCH);
        if (null != search.getResults() && search.getResults().size() > 0) {
          resultVS.setDisplayedChild(ITEM_LIST);
          adapter.setItems(search.getResults());

          adapterNames = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line);
          adapterNames.clear();
          adapterNames.addAll(getNames(search));
          searchET.setAdapter(adapterNames);
        } else {
          resultVS.setDisplayedChild(EMPTY_LIST);
        }
      }
    });
    userViewModel.getMoreData().observe(this, result -> {
      gettingPage = false;
      if (result == null) {
        Toast.makeText(getContext(), "No more items", Toast.LENGTH_LONG).show();
      } else {
        adapter.addItems(result.getResults());
        adapterNames.addAll(getNames(result));
      }
    });

    searchET.setOnEditorActionListener((textView, i, keyEvent) -> {
      if (EditorInfo.IME_ACTION_SEARCH == i) {
        User user = adapter.getUser(searchET.getText().toString());
        if (user != null) {
          onItemClick(searchET, user);
        }
      }
      return false;
    });

    RecyclerView resultRV = view.findViewById(R.id.result_recycler);
    friendsRV = view.findViewById(R.id.friends_recycler);

    adapter = new UsersAdapter();
    adapter.setOnClickListener(this);

    glm = new GridLayoutManager(getContext(), 2);

    resultRV.setLayoutManager(glm);
    resultRV.setAdapter(adapter);
    resultRV.setOnScrollChangeListener((view1, i, i1, i2, i3) -> {
      int a = glm.findLastVisibleItemPosition();
      int b = glm.getItemCount();
      if (b - a < 15 && !gettingPage) {
        gettingPage = true;
        page++;
        userViewModel.getPage(page);
      }
    });

    friendsAdapter = new UsersAdapter();
    friendsAdapter.setOnClickListener(this);

    LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

    friendsRV.setLayoutManager(llm);
    friendsRV.setAdapter(friendsAdapter);
  }

  private List<String> getNames(Result search) {
    List<String> names = new ArrayList<>();
    for (User user : search.getResults()) {
      names.add(user.getName().getFirst() + " " + user.getName().getLast());
    }
    return names;
  }

  @Override
  public void onItemClick(View view, User user) {
    FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment_container, DetailsFragment.newInstance(user));
    transaction.addToBackStack(SearchFragment.class.getName());
    transaction.commit();
  }

  @Override
  public void onResume() {
    super.onResume();
    // Know Issue: Should update all the references of users to work properly, but since the web services never returns the isFriend value
    // I want to avoid make a heavy loop every time, any way you cannot insert the friend twice.
    SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    Set<String> friends = new HashSet<>(sharedPreferences.getStringSet(FIRENDS_LIST, new ArraySet<>()));
    List<User> users = new ArrayList<>();
    Gson gson = new Gson();
    for (String friend : friends) {
      users.add(gson.fromJson(friend, User.class));
    }
    friendsAdapter.clear();
    friendsAdapter.addItems(users);
    if (users.size() > 0) {
      friendsRV.setVisibility(View.VISIBLE);
    }
  }
}
