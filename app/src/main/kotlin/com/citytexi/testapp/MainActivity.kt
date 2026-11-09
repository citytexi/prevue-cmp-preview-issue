package com.citytexi.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.citytexi.testapp.feature.alert.AlertItem
import com.citytexi.testapp.feature.alert.AlertInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlertItem(alert = AlertInfo(title = "Hello", body = "From the app module"), onClick = {})
        }
    }
}
