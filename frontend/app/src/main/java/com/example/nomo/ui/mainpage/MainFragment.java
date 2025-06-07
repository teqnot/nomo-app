package com.example.nomo.ui.mainpage;

import static android.text.TextUtils.replace;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomo.MainActivity;
import com.example.nomo.R;
import com.example.nomo.adapter.DebtListAdapter;
import com.example.nomo.model.Debt;
import com.example.nomo.ui.addticket.AddEntryFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private DebtListAdapter adapter;
    private List<Debt> allDebts = new ArrayList<>();
    private List<Debt> filteredDebts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initTestDebts();

        recyclerView = view.findViewById(R.id.recyclerViewDebts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DebtListAdapter(filteredDebts);
        recyclerView.setAdapter(adapter);

        setupBalanceCards(view);
    }

    private void initTestDebts() {
        allDebts.clear();
        allDebts.add(new Debt("Иван Борисов", "331₽", false));
        allDebts.add(new Debt("Илья Макаров", "8912₽", true));
        allDebts.add(new Debt("Артем Рожков", "87 091₽", true));
        filteredDebts.addAll(allDebts);
    }

    private void setupBalanceCards(View view) {
        String[] amounts = updateBalance();
        boolean shouldUseVertical = amounts[0].length() > 6 || amounts[1].length() > 6;

        ViewStub stub = view.findViewById(R.id.stubBalanceSection);
        if (stub == null) {
            ViewGroup container = view.findViewById(R.id.containerBalanceCards);
            if (container != null) {
                container.removeAllViews();
            }
        } else {
            stub.setLayoutResource(shouldUseVertical ? R.layout.section_balance_vertical : R.layout.section_balance);
            View inflatedView = stub.inflate();
            Log.d("MainFragment", "balance section inflated");
        }

        ViewGroup container = view.findViewById(R.id.containerBalanceCards);

        if (container == null) {
            Log.e("MainFragment", "containerBalanceCards not found");
            return;
        }

        View cardYouOwe = view.findViewById(R.id.balanceYouOweCard);
        updateBalanceCard(
                cardYouOwe,
                "Вы должны:",
                amounts[0],
                ContextCompat.getColor(requireContext(), R.color.you_owe),
                ContextCompat.getColor(requireContext(), R.color.accent_you_owe)
        );
        View cardYouAreOwed = view.findViewById(R.id.balanceYouAreOwedCard);
        updateBalanceCard(
                cardYouAreOwed,
                "Вам должны:",
                amounts[1],
                ContextCompat.getColor(requireContext(), R.color.you_are_owed),
                ContextCompat.getColor(requireContext(), R.color.accent_you_are_owed)
        );

        // Клик по карточке "Вы должны"
        cardYouOwe.setOnClickListener(v -> {
            List<Debt> filteredList = new ArrayList<>();
            for (Debt debt : allDebts) {
                if (!debt.isOwedToMe()) {
                    filteredList.add(debt);
                }
            }
            adapter.updateDebts(filteredList);
        });

        // Клик по карточке "Вам должны"
        cardYouAreOwed.setOnClickListener(v -> {
            List<Debt> filteredList = new ArrayList<>();
            for (Debt debt : allDebts) {
                if (debt.isOwedToMe()) {
                    filteredList.add(debt);
                }
            }
            adapter.updateDebts(filteredList);
        });
    }

    private String[] updateBalance() {
        double totalYouOwe = 0;
        double totalYouAreOwed = 0;

        for (Debt debt : allDebts) {
            try {
                // Чистим строку от всех символов, кроме чисел и точки
                String amountClean = debt.getAmount().replace("₽", "").replace(",", ".").replaceAll("\\s+", "");
                double amount = Double.parseDouble(amountClean);

                if (debt.isOwedToMe()) {
                    totalYouAreOwed += amount;
                } else {
                    totalYouOwe += amount;
                }
            } catch (Exception ignored) {}
        }

        // Форматируем числа: 1234.5 → "1 234,50₽"
        return new String[]{
                formatBalance(totalYouOwe),
                formatBalance(totalYouAreOwed)
        };
    }

    private String formatBalance(double val) {
        if (val <  0) val = 0;

        int intVal = (int) Math.floor(val);
        int decimalVal = (int) Math.round((val - intVal) * 100);

        String intFormatted = String.format("%,d", intVal).replace(',', ' ');
        return String.format("%s,%02d₽", intFormatted, decimalVal);
    }

    private void updateBalanceCard(View cardView, String title, String amount, int bgColor, int lineColor) {
        TextView cardTitle = cardView.findViewById(R.id.cardTitle);
        TextView cardAmount = cardView.findViewById(R.id.cardAmount);
        View cardLine = cardView.findViewById(R.id.cardLine);

        // Обновляем заголовок
        cardTitle.setText(title);

        // Обновляем сумму
        cardAmount.setText(amount);

        // Обновляем фон
        GradientDrawable newBackground = new GradientDrawable();
        newBackground.setCornerRadius(15);
        newBackground.setColor(bgColor);
        cardView.setBackground(newBackground);

        // Обновляем цвет линии
        cardLine.setBackgroundColor(lineColor);

        // Текст подсветка
        cardTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        cardAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
    }
}