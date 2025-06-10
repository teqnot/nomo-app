package com.example.nomo.ui.friends;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nomo.R;
import com.example.nomo.model.Friend;
import com.example.nomo.model.UserDto;
import com.example.nomo.repository.UserRepository;
import com.example.nomo.utils.FriendMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class FriendsFragment extends Fragment {

    @Inject
    UserRepository userRepository;

    private RecyclerView recyclerView;
    private FriendsAdapter adapter;
    private List<Friend> friends = new ArrayList<>(); // Мои друзья
    private List<Friend> searchResults = new ArrayList<>(); // Результаты поиска
    private SearchView searchView;
    private TextView titleMyFriends;
    private boolean isSearchMode = false;


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
        if (query.isEmpty()) {
            restoreOriginalList();
            return;
        }

        titleMyFriends.setText("Результаты поиска:");
        isSearchMode = true;
        adapter.setSearchMode(true);

        userRepository.searchUsers(query, new Callback<>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Friend> results = FriendMapper.mapAll(response.body());

                    searchResults.clear();
                    searchResults.addAll(results);
                    adapter.updateFriends(searchResults);
                } else {
                    Toast.makeText(requireContext(), "Ошибка поиска", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {
                Log.e("FriendsFragment", "Ошибка поиска", t);
                Toast.makeText(requireContext(), "Не удалось выполнить поиск", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void restoreOriginalList() {
        titleMyFriends.setText("Мои друзья:");
        isSearchMode = false;
        adapter.setSearchMode(false);
        adapter.updateFriends(friends);
    }

    private void initTestFriends() {
        friends.clear();
        friends.add(new Friend("Иван Борисов"));
        friends.add(new Friend("Илья Макаров"));
        friends.add(new Friend("Артем Рожков"));
        adapter.updateFriends(friends);
    }
}
