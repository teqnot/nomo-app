package com.example.nomo.ui.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<Friend> friends = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Настройка RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new FriendsAdapter(friends);
        recyclerView.setAdapter(adapter);

        // Заглушка данных
        initTestFriends();
    }

    private void initTestFriends() {
        friends.clear();
        friends.add(new Friend("Иван Борисов"));
        friends.add(new Friend("Илья Макаров"));
        friends.add(new Friend("Артем Рожков"));
        adapter.notifyDataSetChanged();
    }
}
