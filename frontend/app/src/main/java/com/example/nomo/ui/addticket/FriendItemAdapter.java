package com.example.nomo.ui.addticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nomo.R;

import java.util.ArrayList;
import java.util.List;

public class FriendItemAdapter extends ArrayAdapter<Friend> {

    private Context context;
    private List<Friend> friends;
    private int selectionMode;
    private OnSingleFriendSelectedListener onSingleFriendSelected;
    private OnMultipleFriendsSelectedListener onMultipleFriendsSelected;
    private OnFriendUnselectedListener onFriendUnselected;

    public interface OnSingleFriendSelectedListener {
        void onSingleFriendSelected(FriendWithDebt friendWithDebt);
    }

    public interface OnMultipleFriendsSelectedListener {
        void onMultipleFriendsSelected(List<Friend> selectedFriends);
    }

    public interface OnFriendUnselectedListener {
        void onFriendUnselected();
    }

    public FriendItemAdapter(
            Context context,
            List<Friend> friends,
            int selectionMode,
            OnSingleFriendSelectedListener singleListener,
            OnMultipleFriendsSelectedListener multipleListener,
            OnFriendUnselectedListener unselectedListener
    ) {
        super(context, R.layout.item_friend, friends);
        this.context = context;
        this.friends = friends;
        this.selectionMode = selectionMode;
        this.onSingleFriendSelected = singleListener;
        this.onMultipleFriendsSelected = multipleListener;
        this.onFriendUnselected = unselectedListener;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Friend getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        }

        Friend friend = friends.get(position);

        TextView textView = convertView.findViewById(R.id.textFriendName);
        textView.setText(friend.getUsername());

        convertView.setOnClickListener(v -> {
            if (selectionMode == FriendSelectionMode.SINGLE && onSingleFriendSelected != null) {
                onSingleFriendSelected.onSingleFriendSelected(new FriendWithDebt(friend));
            } else if (selectionMode == FriendSelectionMode.MULTIPLE && onMultipleFriendsSelected != null) {
                if (friends.contains(friend)) {
                    friends.remove(friend);
                    onMultipleFriendsSelected.onMultipleFriendsSelected(friends);
                } else {
                    friends.add(friend);
                    onMultipleFriendsSelected.onMultipleFriendsSelected(friends);
                }
                notifyDataSetChanged();
            }

            if (onFriendUnselected != null) {
                onFriendUnselected.onFriendUnselected();
            }
        });

        return convertView;
    }
}