package com.example.nomo.utils;


import com.example.nomo.model.Friend;
import com.example.nomo.model.UserDto;

import java.util.ArrayList;
import java.util.List;

public class FriendMapper {
    public static Friend fromUserDto(UserDto userDto) {
        Friend friend = new Friend(userDto.getUsername());
        friend.setSaved(false);
        return friend;
    }

    public static List<Friend> mapAll(List<UserDto> users) {
        List<Friend> friends = new ArrayList<>();
        for (UserDto dto : users) {
            friends.add(fromUserDto(dto));
        }
        return friends;
    }
}
