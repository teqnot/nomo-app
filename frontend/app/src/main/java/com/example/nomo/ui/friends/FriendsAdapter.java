package com.example.nomo.ui.friends;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nomo.R;
import com.example.nomo.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {

    private List<Friend> friends;
    private boolean isSearchMode = false;
    private static FriendItemListener listener;

    public FriendsAdapter(List<Friend> friends) {
        this.friends = new ArrayList<>(friends);
    }

    public void setFriendItemListener(FriendItemListener listener) {
        this.listener = listener;
    }

    public void updateFriends(List<Friend> newFriends) {
        friends.clear();
        friends.addAll(newFriends);
        notifyDataSetChanged();
    }

    public void setSearchMode(boolean searchMode) {
        isSearchMode = searchMode;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return isSearchMode ? 1 : 0; // 0 - обычный, 1 - поиск
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_friends, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.bind(friend, isSearchMode);
    }

    @Override
    public int getItemCount() { return friends.size(); }

    public void updateSingleItem(Friend friend) {
        int position = friends.indexOf(friend);
        if (position != -1) {
            friends.set(position, friend);
            notifyItemChanged(position);
        }
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final ImageButton buttonAdd;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textFriendsName);
            buttonAdd = itemView.findViewById(R.id.buttonAddFriend);
        }

        public void bind(Friend friend, boolean isSearchMode) {
            textViewName.setText(friend.getUsername());
            buttonAdd.setVisibility(isSearchMode ? View.VISIBLE : View.GONE);
            buttonAdd.setImageResource(friend.isSaved() ? R.drawable.ic_check : R.drawable.ic_plus);

            if (isSearchMode) {
                buttonAdd.setOnClickListener(v -> {
                    Friend updatedFriend = new Friend(friend.getUsername());
                    updatedFriend.setSaved(true);
                    listener.onAddFriend(updatedFriend);
                    buttonAdd.setImageResource(R.drawable.ic_check);
                });
            }
        }
    }
}
