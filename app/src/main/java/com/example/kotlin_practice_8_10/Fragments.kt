package com.example.kotlin_practice_8_10

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.kotlin_practice_8_10.viewModel.MyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Fragment1(navController: NavController, vModel: MyViewModel){
    var login by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = login,
            onValueChange = {login = it},
            label = { Text(text = "login: ") }
        )
        Button(
            onClick = {
                val str = login
                if (str.isEmpty())
                    Toast.makeText(context, "Enter your nickname", Toast.LENGTH_SHORT).show()
                else {
                    vModel.setLogin(str)
                    try {
                    } catch (e: Exception){
                        Log.d("myApp", e.toString())}
                    navController.navigate("Fragment2")
                }
            },
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "continue")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Fragment2(navController: NavController, vModel: MyViewModel){
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "password: ") }
        )
        Button(
            onClick = {
                if (password.isEmpty())
                    Toast.makeText(context, "Enter your password", Toast.LENGTH_SHORT).show()
                else{
                    vModel.setPassword(password)
                    navController.navigate("Fragment3")
                }
            },
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "continue")
        }
        Button(
            onClick = {
                navController.navigate("Fragment1"){
                    popUpTo("Fragment1"){
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "back")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Fragment3(navController: NavController, vModel: MyViewModel){
    var email by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "email: ") }
        )
        Button(
            onClick = {
                if (email.isEmpty())
                    Toast.makeText(context, "Enter your email", Toast.LENGTH_SHORT).show()
                else{
                    vModel.setEmail(email)
                    Toast.makeText(context, "Account info has been entered", Toast.LENGTH_SHORT).show()
                }
                navController.navigate("Fragment1"){
                    popUpTo("Fragment1"){
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "ok")
        }
        Button(
            onClick = {
                navController.navigate("Fragment2"){
                    popUpTo("Fragment2"){
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "back")
        }
    }
}