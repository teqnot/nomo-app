package com.example.nomo.ui.addticket;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nomo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddEntryFragment extends Fragment {

    private boolean isRoomExpanded = false;
    private boolean isEntryExpanded = false;

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
    private FriendWithDebt selectedFriend = null;

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
        selectedUsersContainer = view.findViewById(R.id.selectedUsersContainer);
        editTextEntryName = view.findViewById(R.id.editTextEntryName);
        editTextEntryDescription = view.findViewById(R.id.editTextEntryDescription);
        textAddUserEntry = view.findViewById(R.id.textAddUserEntry);
        selectedFriendContainer = view.findViewById(R.id.selectedFriendContainer);

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

        // Клик на "Добавить пользователей" в комнату
        view.findViewById(R.id.buttonSelectFriendsRoom).setOnClickListener(v ->
                showFriendSelectionDialog(FriendSelectionMode.SINGLE)
        );

        // Клик на "Добавить пользователя" в запись
        view.findViewById(R.id.buttonSelectFriendEntry).setOnClickListener(v ->
                showFriendSelectionDialog(FriendSelectionMode.SINGLE)
        );
    }

    private void showFriendSelectionDialog(int selectionMode) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_friend_selection);

        TextView title = dialog.findViewById(R.id.title);
        ListView listView = dialog.findViewById(R.id.listFriends);
        LinearLayout dropdownInput = dialog.findViewById(R.id.dropdownInput);
        EditText editTextAmount = dialog.findViewById(R.id.editTextDebtAmount);
        Button buttonSave = dialog.findViewById(R.id.buttonSaveDebt);
        Button buttonDone = dialog.findViewById(R.id.buttonDone);

        // Настройка заголовка
        title.setText(selectionMode == FriendSelectionMode.SINGLE ?
                "Выберите пользователя" :
                "Добавьте пользователей");

        // Тестовые данные
        List<Friend> friends = getTestFriends();

        FriendItemAdapter adapter = new FriendItemAdapter(
                requireContext(),
                friends,
                selectionMode,
                friendWithDebt -> {
                    selectedFriend = friendWithDebt;
                    dropdownInput.setVisibility(View.VISIBLE);
                },
                selectedFriends -> {
                    updateSelectedUsersUI();
                    dialog.dismiss();
                },
                () -> {
                    selectedFriend = null;
                    dialog.dismiss();
                }
        );
        listView.setAdapter(adapter);

        listView.setAdapter(adapter);

        // Сохранение долга
        buttonSave.setOnClickListener(v -> {
            String amount = editTextAmount.getText().toString();
            if (!amount.isEmpty() && selectedFriend != null) {
                selectedFriend.setAmount(amount);
                selectedFriend.setSaved(true);
                updateSelectedFriendUI(selectedFriend);
                dialog.dismiss();
            }
        });

        // Готово
        buttonDone.setOnClickListener(v -> {
            if (selectionMode == FriendSelectionMode.SINGLE && selectedFriend != null) {
                updateSelectedFriendUI(selectedFriend);
            } else if (selectionMode == FriendSelectionMode.MULTIPLE && !selectedUsers.isEmpty()) {
                updateSelectedUsersUI();
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private List<Friend> getTestFriends() {
        return Arrays.asList(
                new Friend(22, "testingNewFunctions"),
                new Friend(23, "testingNewFunctions2"),
                new Friend(24, "Alex"),
                new Friend(25, "Max")
        );
    }

    private void updateSelectedFriendUI(FriendWithDebt friendWithDebt) {
        selectedFriendContainer.removeAllViews();
        TextView textView = new TextView(requireContext());
        textView.setText(friendWithDebt.getFriend().getUsername() + ": " + friendWithDebt.getAmount());
        selectedFriendContainer.addView(textView);
    }

    private void updateSelectedUsersUI() {
        selectedUsersContainer.removeAllViews();
        for (Friend friend : selectedUsers) {
            TextView textView = new TextView(requireContext());
            textView.setText(friend.getUsername());
            selectedUsersContainer.addView(textView);
        }
    }
}
