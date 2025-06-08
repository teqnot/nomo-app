package com.example.nomo.ui.addticket;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class AddEntryFragment extends Fragment {

    private static final String ARG_MODE = "selection_mode"; // "mode" для передачи

    private int selectionMode; // SINGLE или MULTIPLE

    // UI Elements
    private LinearLayout sectionCreateRoom;
    private LinearLayout sectionAddEntry;
    private LinearLayout expandedFieldsRoom;
    private LinearLayout expandedFieldsEntry;
    private EditText editTextRoomName;
    private EditText editTextRoomDescription;
    private TextView textAddUserRoom;
    private LinearLayout selectedUsersContainer;
    private EditText editTextEntryName;
    private EditText editTextEntryDescription;
    private TextView textAddUserEntry;
    private LinearLayout selectedFriendContainer;

    private List<Friend> selectedUsers = new ArrayList<>();
    private FriendWithDebt selectedFriendWithDebt = null;

    public static AddEntryFragment newInstance(int mode) {
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, mode);
        AddEntryFragment fragment = new AddEntryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализация View
        sectionCreateRoom = view.findViewById(R.id.sectionCreateRoom);
        sectionAddEntry = view.findViewById(R.id.sectionAddEntry);
        expandedFieldsRoom = view.findViewById(R.id.expandedFieldsRoom);
        expandedFieldsEntry = view.findViewById(R.id.expandedFieldsEntry);
        editTextRoomName = view.findViewById(R.id.editTextRoomName);
        editTextRoomDescription = view.findViewById(R.id.editTextRoomDescription);
        textAddUserRoom = view.findViewById(R.id.textAddUserRoom);
        editTextEntryName = view.findViewById(R.id.editTextEntryName);
        editTextEntryDescription = view.findViewById(R.id.editTextEntryDescription);
        textAddUserEntry = view.findViewById(R.id.textAddUserEntry);

        // Получаем режим из аргументов
        if (getArguments() != null) {
            selectionMode = getArguments().getInt(ARG_MODE, FriendSelectionMode.SINGLE);
        } else {
            selectionMode = FriendSelectionMode.SINGLE; // по умолчанию
        }

        // Обработчики кликов по секциям
        sectionCreateRoom.setOnClickListener(v -> {
            isRoomExpanded = !isRoomExpanded;
            expandedFieldsRoom.setVisibility(isRoomExpanded ? View.VISIBLE : View.GONE);
            if (isRoomExpanded) {
                isEntryExpanded = false;
                expandedFieldsEntry.setVisibility(View.GONE);
            }
        });

        sectionAddEntry.setOnClickListener(v -> {
            isEntryExpanded = !isEntryExpanded;
            expandedFieldsEntry.setVisibility(isEntryExpanded ? View.VISIBLE : View.GONE);
            if (isEntryExpanded) {
                isRoomExpanded = false;
                expandedFieldsRoom.setVisibility(View.GONE);
            }
        });

        view.findViewById(R.id.buttonSelectFriendsRoom).setOnClickListener(v -> {
            showFriendSelectionDialog(FriendSelectionMode.MULTIPLE);
        });

        view.findViewById(R.id.buttonSelectFriendEntry).setOnClickListener(v -> {
            showFriendSelectionDialog(FriendSelectionMode.SINGLE);
        });
    }

    private boolean isRoomExpanded = false;
    private boolean isEntryExpanded = false;

    private void showFriendSelectionDialog(int mode) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_friend_selection);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(Gravity.CENTER);
        }

        TextView title = dialog.findViewById(R.id.title);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewFriends);
        TextView buttonDone = dialog.findViewById(R.id.buttonDone);

        title.setText(mode == FriendSelectionMode.SINGLE ? "Выберите пользователя" : "Добавьте пользователей");

        List<Friend> friends = getTestFriends();

        FriendItemAdapter adapter = new FriendItemAdapter(requireContext(), friends, mode);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        buttonDone.setOnClickListener(v -> {
            List<Friend> selectedList = new ArrayList<>();
            for (Friend friend : friends) {
                if (friend.isSelected()) {
                    selectedList.add(friend);
                }
            }

            if (mode == FriendSelectionMode.SINGLE && !selectedList.isEmpty()) {
                Friend selected = selectedList.get(0);
                updateSelectedFriendUI(selected);
            } else if (mode == FriendSelectionMode.MULTIPLE) {
                selectedUsers.clear();
                selectedUsers.addAll(selectedList);
                updateSelectedUsersUI(selectedList);
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private List<Friend> getTestFriends() {
        return Arrays.asList(
                new Friend( "testingNewFunctions"),
                new Friend("testingNewFunctions2"),
                new Friend("Alex"),
                new Friend("Max")
        );
    }

    private void updateSelectedFriendUI(Friend friendWithDebt) {
        ImageView imageView = expandedFieldsEntry.findViewById(R.id.iconSelectFriendEntry);
        TextView textView = expandedFieldsEntry.findViewById(R.id.textAddUserEntry);

        imageView.setImageResource(R.drawable.ic_check);
        textView.setText(friendWithDebt.getUsername() + ": " + friendWithDebt.getAmount());
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_text_on_hover));
    }

    private void updateSelectedUsersUI(List<Friend> selectedUsers) {
        ImageView imageView = expandedFieldsRoom.findViewById(R.id.iconSelectFriendsRoom);
        TextView textView = expandedFieldsRoom.findViewById(R.id.textAddUserRoom);

        StringJoiner friends = new StringJoiner(", ");

        Log.d("Friends", "FRIENDS:");
        for (Friend friend : selectedUsers) {
            Log.d("Friends", friend.getUsername());
            friends.add(friend.getUsername());
        }

        imageView.setImageResource(R.drawable.ic_check);
        textView.setText(friends.toString());
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_text_on_hover));
    }
}