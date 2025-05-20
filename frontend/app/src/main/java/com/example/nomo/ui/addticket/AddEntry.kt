package com.example.nomo.ui.addticket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nomo.R
import com.example.nomo.network.ApiService.Friend
import com.example.nomo.ui.common.CustomTextField
import com.example.nomo.ui.theme.*
import com.example.nomo.viewmodel.FriendViewModel

@Composable
@Preview(showBackground = true)
fun AddEntryScreenPreview() {
    NomoTheme {
        AddEntryScreen(
            userId = 12312312, // TODO: Handle REAL userId
            onSaveRoom = { name, desc, users -> },
            onSaveEntry = { name, desc, debts -> }
        )
    }
}

@Composable
fun AddEntryScreen(
    userId: Long,
    onSaveRoom: (String, String, List<String>) -> Unit,
    onSaveEntry: (String, String, Map<String, Double>) -> Unit
) {
    var expandedRoom by remember { mutableStateOf(false) }
    var expandedEntry by remember { mutableStateOf(false) }

    val friendViewModel: FriendViewModel = hiltViewModel()
    val friendState by friendViewModel.friendState.collectAsState()

    LaunchedEffect(Unit) {
        friendViewModel.loadFriends(userId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CreateRoomSection(
                expanded = expandedRoom,
                onExpandChange = {
                    expandedRoom = it
                    if (it) expandedEntry = false
                },
                onSave = onSaveRoom
            )
        }

        item {
            AddEntrySection(
                expanded = expandedEntry,
                onExpandChange = {
                    expandedEntry = it
                    if (it) expandedRoom = false
                },
                onSave = onSaveEntry,
                friendState = friendState
            )
        }
    }
}

@Composable
private fun CreateRoomSection(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onSave: (String, String, List<String>) -> Unit
) {
    var roomName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var addUser by remember { mutableStateOf("") }
    var selectedUsers by remember { mutableStateOf(emptyList<Friend>()) }
    val showFriendDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFFF6F0FF))
            .clickable { onExpandChange(!expanded) }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_group),
                contentDescription = null,
                tint = SecondaryTextOnHover
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Создать комнату",
                fontSize = 16.sp,
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_dropdown),
                contentDescription = "Раскрыть"
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 4.dp),
            color = SecondaryTextOnHover,
            thickness = 2.dp
        )

        Text(
            text = "Создайте комнату, чтобы удобно учитывать расходы между целой группой друзей!",
            modifier = Modifier.padding(top = 8.dp),
            fontFamily = SFProText,
            fontSize = 14.sp,
            color = SecondaryTextOnPrimary
        )

        if (expanded) {
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                value = roomName,
                onValueChange = { roomName = it },
                placeholder = "Название",
                leadingIcon = R.drawable.ic_tag
            )

            Spacer(Modifier.height(16.dp))
            CustomTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = "Описание",
                leadingIcon = R.drawable.ic_description
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .clickable { showFriendDialog.value = true }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = "Add Users",
                    tint = SecondaryTextOnBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Добавить пользователей",
                    fontSize = 16.sp,
                    fontFamily = SFProText,
                    fontWeight = FontWeight.Medium,
                    color = SecondaryTextOnBackground,
                )
            }

            if (selectedUsers.isNotEmpty()) {
                LazyColumn {
                    items(selectedUsers.size) { index ->
                        Text(
                            text = selectedUsers[index].username,
                            fontSize = 14.sp,
                            fontFamily = SFProText
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddEntrySection(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onSave: (String, String, Map<String, Double>) -> Unit,
    friendState: FriendViewModel.FriendState
) {
    var entryName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var selectedFriends = remember { mutableStateListOf<FriendWithDebt>() }

    val showFriendDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFFF6F0FF))
            .clickable { onExpandChange(!expanded) }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_person),
                contentDescription = null,
                tint = SecondaryTextOnHover
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Добавить запись",
                fontSize = 16.sp,
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_dropdown),
                contentDescription = "Раскрыть"
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 4.dp),
            color = SecondaryTextOnHover,
            thickness = 2.dp
        )

        Text(
            text = "Добавьте индивидуальную запись и начните уже сейчас следить за своими расходами!",
            modifier = Modifier.padding(top = 8.dp),
            fontFamily = SFProText,
            fontSize = 14.sp,
            color = SecondaryTextOnPrimary
        )

        if (expanded) {
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                value = entryName,
                onValueChange = { entryName = it },
                placeholder = "Название",
                leadingIcon = R.drawable.ic_tag
            )

            Spacer(Modifier.height(16.dp))
            CustomTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = "Описание",
                leadingIcon = R.drawable.ic_description
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .clickable { showFriendDialog.value = true }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = "Add Users",
                    tint = SecondaryTextOnBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Добавить пользователя",
                    fontSize = 16.sp,
                    fontFamily = SFProText,
                    fontWeight = FontWeight.Medium,
                    color = SecondaryTextOnBackground,
                )
            }

            if (selectedFriends.isNotEmpty()) {
                LazyColumn {
                    items(selectedFriends.size) { index ->
                        val item = selectedFriends[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.friend.username,
                                fontSize = 14.sp,
                                fontFamily = SFProText
                            )
                            Text(
                                text = item.amount,
                                fontSize = 14.sp,
                                fontFamily = SFProText
                            )
                        }
                    }
                }
            }
        }
    }

    if (showFriendDialog.value) {
        FriendSelectionDialog(
            friends = when (friendState) {
                is FriendViewModel.FriendState.Success -> (friendState as FriendViewModel.FriendState.Success).friends
                else -> emptyList()
            },
            selectionMode = FriendSelectionMode.SINGLE,
            onFriendSelected = {
                if (it.isNotEmpty()) {
                    // TODO: Update state (API)
                }
            },
            onDismiss = {
                showFriendDialog.value = false
            }
        )
    }
}
