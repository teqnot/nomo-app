package com.example.nomo.ui.addticket

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nomo.R
import com.example.nomo.network.ApiService.Friend
import com.example.nomo.ui.theme.DividerColorBalances
import com.example.nomo.ui.theme.DropdownBgColor
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.SecondaryTextOnBackground
import com.example.nomo.ui.theme.SecondaryTextOnHover
import androidx.compose.ui.Modifier
import kotlin.math.log

@Composable
fun FriendSelectionDialog(
    friends: List<Friend>,
    selectionMode: FriendSelectionMode,
    onSingleFriendSelected: (FriendWithDebt) -> Unit,
    onMultipleFriendsSelected: (List<Friend>) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = DropdownBgColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (selectionMode) {
                            FriendSelectionMode.SINGLE -> "Выберите пользователя"
                            FriendSelectionMode.MULTIPLE -> "Добавьте пользователей"
                        },
                            fontSize = 16.sp,
                            fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(top = 4.dp),
                    color = SecondaryTextOnHover,
                    thickness = 2.dp
                )

                if (friends.isEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Нет доступных друзей",
                        fontSize = 16.sp,
                        fontFamily = SFProDisplay,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    val selectedSingleFriend = remember { mutableStateOf<FriendWithDebt?>(null) }
                    val selectedMultipleFriends = remember { mutableStateListOf<Friend>() }

                    LazyColumn {
                        items(friends.size) { index ->
                            val friend = friends[index]

                            val isSelected = when (selectionMode) {
                                FriendSelectionMode.SINGLE -> selectedSingleFriend.value?.friend == friend
                                FriendSelectionMode.MULTIPLE -> selectedMultipleFriends.contains(friend)
                            }

                            val isSaved = selectedSingleFriend.value?.isSaved == true

                            FriendItem(
                                friend = friend,
                                isSelected = isSelected,
                                isSaved = isSaved,
                                selectionMode = selectionMode,
                                onSelect = {
                                    when (selectionMode) {
                                        FriendSelectionMode.SINGLE -> {
                                            selectedSingleFriend.value = FriendWithDebt(friend = friend)
                                        }
                                        FriendSelectionMode.MULTIPLE -> {
                                            selectedMultipleFriends.add(friend)
                                        }
                                    }
                                },
                                onUnselect = {
                                    when (selectionMode) {
                                        FriendSelectionMode.SINGLE -> {
                                            selectedSingleFriend.value = null
                                        }
                                        FriendSelectionMode.MULTIPLE -> {
                                            selectedMultipleFriends.remove(friend)
                                        }
                                    }
                                },
                                onSaveDebt = { amount ->
                                    selectedSingleFriend.value = selectedSingleFriend.value?.copy(
                                        amount = amount,
                                        isSaved = true
                                    )
                                    onSingleFriendSelected(selectedSingleFriend.value!!)
                                    onDismiss()
                                }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            when (selectionMode) {
                                FriendSelectionMode.SINGLE -> {
                                    if (selectedSingleFriend.value?.amount?.isNotBlank() == true) {
                                        onSingleFriendSelected(selectedSingleFriend.value!!)
                                    }
                                }
                                FriendSelectionMode.MULTIPLE -> {
                                    if (selectedMultipleFriends.isNotEmpty()) {
                                        onMultipleFriendsSelected(selectedMultipleFriends)
                                    }
                                }
                            }
                            onDismiss()
                        }) {
                            Text(
                                text = "Готово",
                                fontSize = 16.sp,
                                fontFamily = SFProDisplay
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FriendItem(
    friend: Friend,
    isSaved: Boolean,
    isSelected: Boolean,
    selectionMode: FriendSelectionMode,
    onSelect: () -> Unit,
    onUnselect: () -> Unit,
    onSaveDebt: (String) -> Unit
) {
    var showDropdown by remember { mutableStateOf(false) }

    val currentIsSelected = isSelected
    val currentIsSaved = isSaved

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when (selectionMode) {
                            FriendSelectionMode.SINGLE -> {
                                if (!currentIsSaved) {
                                    if (!currentIsSelected) {
                                        onSelect()
                                        showDropdown = true
                                    } else {
                                        showDropdown = !showDropdown
                                        if (!showDropdown) {
                                            onUnselect()
                                        }
                                    }
                                }
                            }

                            FriendSelectionMode.MULTIPLE -> {
                                if (currentIsSelected) {
                                    onUnselect()
                                } else {
                                    onSelect()
                                }
                            }
                        }
                    }
                    .padding(vertical = 12.dp)
            ) {
                Image( // TODO: Handle REAL images as avatars
                    painter = painterResource(id = R.drawable.ic_avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = friend.username,
                    fontSize = 16.sp,
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(0.9f))
                Icon(
                    painter = painterResource(
                        id = if (currentIsSelected) R.drawable.ic_check else R.drawable.ic_plus
                    ),
                    contentDescription = if (currentIsSelected) "Доабвлен" else "Добавить",
                    tint = if (currentIsSelected) SecondaryTextOnHover else SecondaryTextOnBackground
                )
            }

            HorizontalDivider(
                color = DividerColorBalances,
                thickness = 2.dp
            )
        }
    }

    Log.d("DBG", "--------------------------------------")
    Log.d("DBG", "FriendItem, selection mode: " + selectionMode)
    Log.d("DBG", "FriendItem, currentIsSelected: " + currentIsSelected)
    Log.d("DBG", "FriendItem, currentIsSaved: " + currentIsSaved)
    Log.d("DBG", "FriendItem, showDropdown: " + showDropdown)

    if (selectionMode == FriendSelectionMode.SINGLE && currentIsSelected && !currentIsSaved && showDropdown) {
        DebtInputDropdown(
            initialAmount = "",
            onSave = { amount ->
                onSaveDebt(amount)
                showDropdown = false
            },
            onClose = {
                showDropdown = false
            }
        )
    }
}