package com.example.nomo.ui.mainpage;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomo.R;
import com.example.nomo.api.DebtApi;
import com.example.nomo.model.Debt;
import com.example.nomo.model.PayDebtRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DebtListAdapter extends RecyclerView.Adapter<DebtListAdapter.DebtViewHolder> {

    private DebtApi debtApi;
    private List<Debt> debts;
    private Context context;

    public DebtListAdapter(List<Debt> debts, DebtApi debtApi) {
        this.debts = new ArrayList<>(debts);
        this.debtApi = debtApi;
    }

    public void updateDebts(List<Debt> newDebts) {
        debts.clear();
        debts.addAll(newDebts);
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
            toggleDropdown(holder.dropdownInput, debt);
        });
    }

    private void toggleDropdown(View view, Debt debt) {
        view.setVisibility(debt.isOpened() ? View.VISIBLE : View.GONE);

        EditText editTextSum = view.findViewById(R.id.editTextSumPay);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> {
            String amountStr = editTextSum.getText().toString().trim();

            if (amountStr.isEmpty()) {
                Toast.makeText(context, "Введите сумму", Toast.LENGTH_SHORT).show();
                return;
            }

            Double amountPaid = Double.parseDouble(amountStr);
            Log.d("REQUEST", "->>>> " + debt.getId() + " " + amountPaid);
            PayDebtRequest request = new PayDebtRequest(debt.getId(), amountPaid);

            debtApi.payDebt(request).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Сумма учтена", Toast.LENGTH_SHORT).show();
                        view.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(context, "Ошибка при оплате", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("DebtListAdapter", "Ошибка оплаты долга ", t);
                    Toast.makeText(context, "Ошибка сети", Toast.LENGTH_SHORT).show();
                }
            });
        });
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