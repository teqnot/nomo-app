package com.example.nomo.ui.addticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomo.R;

import java.util.List;

public class FriendItemAdapter extends RecyclerView.Adapter<FriendItemAdapter.FriendViewHolder> {
    private final Context context;
    private final List<Friend> friends;
    private final int selectionMode;

    public FriendItemAdapter(Context context, List<Friend> friends, int selectionMode) {
        this.context = context;
        this.friends = friends;
        this.selectionMode = selectionMode;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friends.get(position);

        holder.textUsername.setText(friend.getUsername());
        boolean isSelected = friend.isSelected();

        if (selectionMode == FriendSelectionMode.SINGLE) {
            holder.iconCheck.setImageResource(isSelected ? R.drawable.ic_check : R.drawable.ic_plus);
            holder.iconCheck.setColorFilter(
                    context.getResources().getColor(
                            isSelected
                                    ? R.color.secondary_text_on_hover
                                    : R.color.secondary_text_on_background
                    )
            );
        } else {
            holder.iconCheck.setImageResource(isSelected ? R.drawable.ic_check : R.drawable.ic_plus);
            holder.iconCheck.setVisibility(View.VISIBLE);
        }

        if (selectionMode == FriendSelectionMode.SINGLE && isSelected && !friend.isSaved()) {
            holder.dropdownInput.setVisibility(View.VISIBLE);
        } else {
            holder.dropdownInput.setVisibility(View.GONE);
        }

        holder.friendRow.setOnClickListener(v -> {
            if (selectionMode == FriendSelectionMode.SINGLE) {
                boolean wasSelected = friend.isSelected();

                for (Friend f : friends) {
                    f.setSelected(false);
                }

                if (!wasSelected) {
                    friend.setSelected(true);
                } else {
                    friend.setSelected(false);
                }

                notifyDataSetChanged();
            } else {
                // MULTIPLE
                friend.setSelected(!friend.isSelected());
                notifyDataSetChanged();
            }
        });

        holder.buttonSaveDebt.setOnClickListener(v -> {
            String amount = holder.editTextSum.getText().toString().trim();
            if (!amount.isEmpty()) {
                friend.setAmount(amount);
                friend.setSaved(true);
                notifyDataSetChanged(); // Прячем dropdown после сохранения
            } else {
                Toast.makeText(context, "Введите сумму", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        ImageView iconCheck;
        LinearLayout dropdownInput;
        LinearLayout friendRow;
        EditText editTextSum;
        Button buttonSaveDebt;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.textUsername);
            iconCheck = itemView.findViewById(R.id.iconCheck);
            dropdownInput = itemView.findViewById(R.id.dropdownInput);
            friendRow = itemView.findViewById(R.id.friendRow);
            editTextSum = itemView.findViewById(R.id.editTextSum);
            buttonSaveDebt = itemView.findViewById(R.id.buttonSaveDebt);
        }
    }
}