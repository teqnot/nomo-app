package com.example.nomo.ui.friends;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nomo.R;
import com.example.nomo.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FriendsAdapter adapter;
    private List<Friend> friends = new ArrayList<>(); // Мои друзья
    private List<Friend> searchResults = new ArrayList<>(); // Результаты поиска
    private SearchView searchView;
    private TextView titleMyFriends;
    private boolean isSearchMode = false;
    private List<Friend> allUsers; // Заглушка на время тестирования UI

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Настройка RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewMyFriends);
        searchView = view.findViewById(R.id.searchViewFriends);
        titleMyFriends = view.findViewById(R.id.titleMyFriends);

        if (recyclerView == null) {
            Log.e("FriendsFragment", "recyclerView == null");
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new FriendsAdapter(friends);
        adapter.setFriendItemListener(clickedFriend -> {
            if (!friends.contains(clickedFriend)) {
                Friend friendToAdd = new Friend(clickedFriend.getUsername());
                friends.add(friendToAdd);
            }
            int pos = searchResults.indexOf(clickedFriend);
            if (pos != -1) {
                clickedFriend.setSaved(true);
                adapter.updateSingleItem(clickedFriend);
            }
        });
        recyclerView.setAdapter(adapter);

        // Заглушка
        initTestFriends();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    restoreOriginalList();
                } else {
                    startSearch(newText);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            restoreOriginalList();
            return false;
        });
    }

    private void startSearch(String query) {
        searchResults.clear();

        for (Friend friend : getAllUsers()) {
            if (!friend.getUsername().toLowerCase().contains(query.toLowerCase())) continue;

            boolean alreadyAdded = false;
            for (Friend f : friends) {
                if (f.getUsername().equals(friend.getUsername())) {
                    alreadyAdded = true;
                    break;
                }
            }

            if (!alreadyAdded) {
                Friend copy = new Friend(friend.getUsername());
                copy.setSaved(false);
                searchResults.add(copy);
            }
        }

        titleMyFriends.setText("Результаты поиска:");
        isSearchMode = true;
        adapter.setSearchMode(true);
        adapter.updateFriends(searchResults);
    }

    private void restoreOriginalList() {
        titleMyFriends.setText("Мои друзья:");
        isSearchMode = false;
        adapter.setSearchMode(false);
        adapter.updateFriends(friends);
    }

    private List<Friend> getAllUsers() {
        if (allUsers == null) {
            allUsers = new ArrayList<>();
            allUsers.add(new Friend("test1"));
            allUsers.add(new Friend("test2"));
            allUsers.add(new Friend("test3"));
            allUsers.add(new Friend("test4"));
            allUsers.add(new Friend("test5"));
        }
        return allUsers;
    }

    private void initTestFriends() {
        friends.clear();
        friends.add(new Friend("Иван Борисов"));
        friends.add(new Friend("Илья Макаров"));
        friends.add(new Friend("Артем Рожков"));
        adapter.updateFriends(friends);
    }
}
