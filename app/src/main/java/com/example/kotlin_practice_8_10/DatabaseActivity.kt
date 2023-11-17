package com.example.kotlin_practice_8_10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_practice_8_10.database.MainDB
import com.example.kotlin_practice_8_10.database.UserDB
import com.example.kotlin_practice_8_10.retrofit.User
import com.example.kotlin_practice_8_10.ui.theme.Kotlin_practice_810Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class DatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = MainDB.getDb(this)
            val users by db.getDao().getAllUsers().collectAsState(initial = emptyList())

            UserListScreen(users)
        }
    }
}
@Composable
fun UserListScreen(users: List<UserDB>) {
    LazyColumn {
        items(users.size) { index ->
            UserListItem(users[index])
        }
    }
}
@Composable
fun UserListItem(user: UserDB) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Id: ${user.id}", fontWeight = FontWeight.Bold)
        Text(text = "Nickname: ${user.nickname}")
        Text(text = "Password: ${user.password}")
        Text(text = "Email: ${user.email}")
    }
}
