package com.example.kotlin_practice_8_10.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_practice_8_10.database.MainDB
import com.example.kotlin_practice_8_10.database.UserDB
import com.example.kotlin_practice_8_10.retrofit.MainAPI
import com.example.kotlin_practice_8_10.retrofit.User
import com.example.kotlin_practice_8_10.viewModel.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import java.util.Random

@Composable
fun FirstScreen(vModel: MyViewModel, db: MainDB) {
    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = "Fragment1"
        ) {
            composable("Fragment1") {
                Fragment1(navController, vModel)
            }
            composable("Fragment2") {
                Fragment2(navController, vModel)
            }
            composable("Fragment3") {
                Fragment3(navController, vModel)
            }
        }
        AccountInfo(vModel)
        WorkWithAccountInfo(vModel, db)
    }
}

@Composable
fun AccountInfo(vModel: MyViewModel) {
    val loginState = vModel.getLogin().observeAsState()
    val passwordState = vModel.getPassword().observeAsState()
    val emailState = vModel.getEmail().observeAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(text = "Login: ${loginState.value}")
        Text(text = "Password: ${passwordState.value}")
        Text(text = "Email: ${emailState.value}")
    }
}

@Composable
fun WorkWithAccountInfo(vModel: MyViewModel, db: MainDB) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { generateAccountInfo(context, vModel) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "generate account info")
        }
        Button(
            onClick = {
                cleanVModel(vModel)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "clean account info")
        }
        Button(
            onClick = {
                createAccount(vModel, db, context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "CREATE ACCOUNT")
        }
    }
}

fun cleanVModel(vModel: MyViewModel) {
    vModel.setLogin(null)
    vModel.setPassword(null)
    vModel.setEmail(null)
}
fun generateAccountInfo(context: Context, vModel: MyViewModel) {
    var user: User
    val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val userAPI = retrofit.create(MainAPI::class.java)
    val num = 50
    CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main)
        {
            try {
                user = userAPI.getUserById(Random().nextInt(num) + 1)
                Log.d("myApp", user.username + " " + user.password + " " + user.email)
                vModel.setLogin(user.username)
                vModel.setPassword(user.password)
                vModel.setEmail(user.email)
                Toast.makeText(
                    context,
                    "The account details was successfully generated",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: UnknownHostException) {
                Toast.makeText(
                    context,
                    "Failed to generate: check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Log.e("myApp", "error: $e")
            }
        }
    }
}

fun createAccount(vModel: MyViewModel, db: MainDB, context: Context) {
    if (checkAccountInfo(vModel)) {
        try {
            val user = UserDB(
                null, vModel.getLogin().value.toString(),
                vModel.getPassword().value.toString(),
                vModel.getEmail().value.toString()
            )
            cleanVModel(vModel)
            Thread {
                db.getDao().insertUser(user)
            }.start()
            Toast.makeText(
                context,
                "The account was successfully entered into the database",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Log.e("myApp", e.toString())
        }
    } else
        Toast.makeText(context, "Fill out the fields", Toast.LENGTH_SHORT).show()
}

fun checkAccountInfo(vModel: MyViewModel): Boolean {
    return vModel.getLogin().value != null
}