package com.example.nomo.ui.addticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nomo.R
import com.example.nomo.network.ApiService.Friend
import com.example.nomo.ui.mainpage.TicketDropdown
import com.example.nomo.ui.theme.DividerColorBalances
import com.example.nomo.ui.theme.DropdownBgColor
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.SecondaryTextOnBackground
import com.example.nomo.ui.theme.SecondaryTextOnHover
import androidx.compose.ui.Modifier

@Composable
fun FriendSelectionDialog(
    friends: List<Friend>,
    selectionMode: FriendSelectionMode,
    onFriendSelected: (List<FriendWithDebt>) -> Unit,
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
                    val selectedFriends = remember { mutableStateListOf<FriendWithDebt>() }

                    LazyColumn {
                        items(friends.size) { index ->
                            val friend = friends[index]
                            val friendWithDebt = FriendWithDebt(friend)

                            // Если уже выбран — берем из списка
                            val current = selectedFriends.find { it.friend.id == friend.id }
                            val isAdded = current != null

                            FriendItem(
                                friend = friend,
                                isAdded = isAdded,
                                onToggle = {
                                    if (isAdded) {
                                        selectedFriends.removeIf { it.friend.id == friend.id }
                                    } else {
                                        selectedFriends.add(FriendWithDebt(friend.copy()))
                                    }
                                },
                                onClick = {
                                    if (selectionMode == FriendSelectionMode.SINGLE) {
                                        selectedFriends.clear()
                                        selectedFriends.add(FriendWithDebt(friend.copy()))
                                        onFriendSelected(selectedFriends)
                                        onDismiss()
                                    } else {
                                        // Handled through onToggle()
                                    }
                                }
                            )
                        }
                    }

                    DisposableEffect(Unit) {
                        onFriendSelected(selectedFriends)
                        onDispose {}
                    }
                }
            }
        }
    }
}

@Composable
fun FriendItem(
    friend: Friend,
    isAdded: Boolean?,
    onToggle: () -> Unit,
    onClick: () -> Unit)
{
    var showDropdown by remember { mutableStateOf(false) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (isAdded == true) {
                        showDropdown = !showDropdown
                    } else {
                        onClick()
                        onToggle()
                    }
                }
                .padding(vertical = 12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_avatar),
                contentDescription = "Avatar"
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
                    id = if (isAdded == true) R.drawable.ic_check else R.drawable.ic_plus
                ),
                contentDescription = if (isAdded == true) "Доабвлен" else "Добавить",
                tint = SecondaryTextOnBackground
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp),
            color = DividerColorBalances,
            thickness = 2.dp
        )

        if (isAdded == true && showDropdown) {
            TicketDropdown(
                initialAmount = "",
                onAmountChange = { newAmount ->
                    // TODO: Find friend, update amount
                },
                onSave = {

                },
                onClose = {
                    showDropdown = false
                }
            )
        }
    }
}