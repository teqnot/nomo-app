package com.example.nomo.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomo.R;
import com.example.nomo.model.Debt;

import java.util.List;

public class DebtListAdapter extends RecyclerView.Adapter<DebtListAdapter.DebtViewHolder> {

    private List<Debt> debts;
    private Context context;

    public DebtListAdapter(List<Debt> debts) {
        this.debts = debts;
    }

    public void updateDebts(List<Debt> newDebts) {
        this.debts.clear();
        this.debts.addAll(newDebts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_debt, parent, false);
        return new DebtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtViewHolder holder, int position) {
        Debt debt = debts.get(position);

        holder.textFriendName.setText(debt.getName());
        holder.textDebtAmount.setText(debt.getAmount());

        // Подсветка суммы
        if (debt.isOwedToMe()) {
            holder.amountContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.balance_amount_tag));
            holder.amountContainer.getBackground().setColorFilter(
                    new PorterDuffColorFilter(
                            ContextCompat.getColor(context, R.color.you_are_owed),
                            PorterDuff.Mode.SRC_IN
                    )
            );
        } else {
            holder.amountContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.balance_amount_tag));
            holder.amountContainer.getBackground().setColorFilter(
                    new PorterDuffColorFilter(
                            ContextCompat.getColor(context, R.color.you_owe),
                            PorterDuff.Mode.SRC_IN
                    )
            );
        }

        // Открытие dropdown
        holder.itemView.setOnClickListener(v -> {
            debt.setOpened(!debt.isOpened());
            toggleDropdown(holder.dropdownInput, debt.isOpened());
        });
    }

    private void toggleDropdown(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return debts.size();
    }

    static class DebtViewHolder extends RecyclerView.ViewHolder {
        TextView textFriendName;
        TextView textDebtAmount;
        LinearLayout dropdownInput;
        LinearLayout amountContainer;

        public DebtViewHolder(@NonNull View itemView) {
            super(itemView);

            textFriendName = itemView.findViewById(R.id.textFriendName);
            textDebtAmount = itemView.findViewById(R.id.textDebtAmount);
            dropdownInput = itemView.findViewById(R.id.dropdownInput);
            amountContainer = itemView.findViewById(R.id.amountContainer);
        }
    }
}