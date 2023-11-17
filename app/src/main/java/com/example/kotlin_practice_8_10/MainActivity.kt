package com.example.kotlin_practice_8_10

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_practice_8_10.retrofit.MainAPI
import com.example.kotlin_practice_8_10.retrofit.User
import com.example.kotlin_practice_8_10.ui.theme.Kotlin_practice_810Theme
import com.example.kotlin_practice_8_10.viewModel.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import java.util.Random

class MainActivity : ComponentActivity() {
    private lateinit var vModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            vModel = ViewModelProvider(this)[MyViewModel::class.java]

            val navController = rememberNavController()
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "Fragment1"
                ){
                    composable("Fragment1"){
                        Fragment1(navController, vModel)
                    }
                    composable("Fragment2"){
                        Fragment2(navController, vModel)
                    }
                    composable("Fragment3"){
                        Fragment3(navController, vModel)
                    }
                }
                AccountInfo(vModel)
                WorkWithAccountInfo(vModel)
            }
            CreateAccount_goToDB_buttons()
        }
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
fun WorkWithAccountInfo(vModel: MyViewModel) {
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
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "clean account info")
        }
    }
}
fun generateAccountInfo(context: Context, vModel: MyViewModel){
    var user: User
    val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val userAPI = retrofit.create(MainAPI::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main)
        {
            try {
                user = userAPI.getUserById(Random().nextInt(50) + 1)
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

@Preview(showBackground = true)
@Composable
fun CreateAccount_goToDB_buttons() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "create account")
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "go to database")
        }
    }
}