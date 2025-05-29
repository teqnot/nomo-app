package com.example.nomo.ui.mainpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nomo.R;
import android.widget.LinearLayout;

public class BalanceCardFragment extends Fragment {

    private String title = "";
    private String amount = "0₽";
    private int bgColor = R.color.you_owe; // по умолчанию
    private int lineColor = R.color.accent_you_owe;

    public static BalanceCardFragment newInstance(String title, String amount, int bgColor, int lineColor) {
        BalanceCardFragment fragment = new BalanceCardFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("amount", amount);
        args.putInt("bgColor", bgColor);
        args.putInt("lineColor", lineColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance_card, container, false);

        TextView cardTitle = view.findViewById(R.id.cardTitle);
        TextView cardAmount = view.findViewById(R.id.cardAmount);
        View cardLine = view.findViewById(R.id.cardLine);
        LinearLayout cardContainer = view.findViewById(R.id.cardContainer);

        if (getArguments() != null) {
            title = getArguments().getString("title", "");
            amount = getArguments().getString("amount", "0₽");
            bgColor = getArguments().getInt("bgColor", R.color.you_owe);
            lineColor = getArguments().getInt("lineColor", R.color.accent_you_owe);
        }

        cardTitle.setText(title);
        cardAmount.setText(amount);
        cardContainer.setBackgroundColor(getResources().getColor(bgColor));
        cardLine.setBackgroundColor(getResources().getColor(lineColor));

        return view;
    }
}