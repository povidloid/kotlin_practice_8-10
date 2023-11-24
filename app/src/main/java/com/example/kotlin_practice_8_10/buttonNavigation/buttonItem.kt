package com.example.kotlin_practice_8_10.buttonNavigation

import com.example.kotlin_practice_8_10.R

sealed class ButtonItem(val title: String, val iconId: Int, val route: String) {
    object Window1 : ButtonItem("MainActivity", R.drawable.home, "Screen1")
    object Window2 : ButtonItem("DatabaseActivity", R.drawable.bookmare, "Screen2")
    object Window3 : ButtonItem("Download photo", R.drawable.baseline_photo, "Screen3")
}