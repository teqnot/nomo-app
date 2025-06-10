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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomo.R;
import com.example.nomo.api.DebtApi;
import com.example.nomo.model.DebtRequest;
import com.example.nomo.model.Friend;
import com.example.nomo.model.UserDto;
import com.example.nomo.repository.UserRepository;
import com.example.nomo.utils.FriendMapper;
import com.example.nomo.utils.SharedPrefManager;
import com.example.nomo.viewmodel.DebtViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class AddEntryFragment extends Fragment {

    @Inject
    SharedPrefManager sharedPrefManager;

    @Inject
    UserRepository userRepository;

    @Inject
    DebtApi debtApi;

    // UI Elements
    private LinearLayout sectionAddEntry;
    private LinearLayout expandedFieldsEntry;
    private EditText editTextEntryName;
    private EditText editTextEntryDescription;
    private TextView textAddUserEntry;
    private FriendWithDebt selectedFriendWithDebt = null;
    private Button buttonSaveDebt;
    private EditText editTextSum;
    private DebtViewModel debtViewModel;

    public static AddEntryFragment newInstance(int mode) {
        Bundle args = new Bundle();
        AddEntryFragment fragment = new AddEntryFragment();
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
        sectionAddEntry = view.findViewById(R.id.sectionAddEntry);
        expandedFieldsEntry = view.findViewById(R.id.expandedFieldsEntry);
        editTextEntryName = view.findViewById(R.id.editTextEntryName);
        editTextEntryDescription = view.findViewById(R.id.editTextEntryDescription);
        textAddUserEntry = view.findViewById(R.id.textAddUserEntry);
        buttonSaveDebt = view.findViewById(R.id.buttonSaveEntry);
        editTextSum = view.findViewById(R.id.editTextSum);

        debtViewModel = new ViewModelProvider(this).get(DebtViewModel.class);

        // Обработчики клика по секции
        sectionAddEntry.setOnClickListener(v -> {
            isEntryExpanded = !isEntryExpanded;
            expandedFieldsEntry.setVisibility(isEntryExpanded ? View.VISIBLE : View.GONE);
        });

        view.findViewById(R.id.buttonSelectFriendEntry).setOnClickListener(v -> {
            showFriendSelectionDialog(FriendSelectionMode.SINGLE);
        });

        buttonSaveDebt.setOnClickListener(v -> {
            if (selectedFriendWithDebt == null) {
                Toast.makeText(requireContext(), "Выберите пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = editTextEntryName.getText().toString().trim();
            String description = editTextEntryDescription.getText().toString().trim();
            String amountStr = selectedFriendWithDebt.getAmount();

            if (name.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            Long creditorId = sharedPrefManager.getUserId();
            Long debtorId = selectedFriendWithDebt.getFriend().getId();

            DebtRequest request = new DebtRequest(debtorId, creditorId, amount, name, description);

            sendDebtToServer(request);
        });
    }

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

        title.setText("Выберите пользователя");

        long currentUserId = sharedPrefManager.getUserId();

        loadMyFriends(currentUserId, friends -> {
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

                if (!selectedList.isEmpty()) {
                    Friend selected = selectedList.get(0);
                    updateSelectedFriendUI(selected);
                }

                dialog.dismiss();
            });
        });

        dialog.show();
    }

    private void updateSelectedFriendUI(Friend friendWithDebt) {
        ImageView imageView = expandedFieldsEntry.findViewById(R.id.iconSelectFriendEntry);
        TextView textView = expandedFieldsEntry.findViewById(R.id.textAddUserEntry);

        imageView.setImageResource(R.drawable.ic_check);
        textView.setText(friendWithDebt.getUsername() + ": " + friendWithDebt.getAmount());
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_text_on_hover));

        selectedFriendWithDebt = new FriendWithDebt(friendWithDebt);
        selectedFriendWithDebt.setId(friendWithDebt.getId());
        selectedFriendWithDebt.setUsername(friendWithDebt.getUsername());
        selectedFriendWithDebt.setAmount(friendWithDebt.getAmount());
    }

    private void loadMyFriends(long userId, FriendsCallback callback) {
        userRepository.getMyFriends(userId, new Callback<>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Friend> friends = FriendMapper.mapAll(response.body());
                    callback.onSuccess(friends);
                } else {
                    Toast.makeText(requireContext(), "Ошибка загрузки друзей", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {
                Log.e("AddEntryFragment", "Ошибка загрузки друзей " + t);
                Toast.makeText(requireContext(), "Ошибка сети", Toast.LENGTH_SHORT).show();
            }
        });
    }

    interface FriendsCallback {
        void onSuccess(List<Friend> friends);
    }

    private void sendDebtToServer(DebtRequest request) {
        debtApi.createDebt(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Долг создан", Toast.LENGTH_SHORT).show();
                    expandedFieldsEntry.setVisibility(View.GONE);
                } else {
                    Toast.makeText(requireContext(), "Ошибка создания долга", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("CreateDebt", "Ошибка создания долга " + t);
                Toast.makeText(requireContext(), "Ошибка сети", Toast.LENGTH_SHORT).show();
            }
        });
    }
}