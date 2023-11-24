package com.example.kotlin_practice_8_10.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kotlin_practice_8_10.database.MainDB
import com.example.kotlin_practice_8_10.database.UserDB

@Composable
fun SecondScreen(db: MainDB) {
    val users by db.getDao().getAllUsers().collectAsState(initial = emptyList())
    UserListScreen(users)
}

@Composable
fun UserListScreen(users: List<UserDB>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(users.size) { index ->
            UserListItem(users[index])
        }
    }
}

@Composable
fun UserListItem(user: UserDB) {
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = "Id: ${user.id}", fontWeight = FontWeight.Bold)
        Text(text = "Nickname: ${user.nickname}")
        Text(text = "Password: ${user.password}")
        Text(text = "Email: ${user.email}")
    }
}