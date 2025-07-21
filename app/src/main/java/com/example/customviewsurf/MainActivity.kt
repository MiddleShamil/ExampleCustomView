package com.example.customviewsurf

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val customView = findViewById<ExampleCustomView>(R.id.custom_view)
        customView.setColors(
            listOf(
                Color.BLACK,
                Color.RED,
                Color.CYAN
            )
        )

//        customView.setHexColors(
//            listOf(
//                "#00FFFF",
//                "#F0F0F0",
//                "#FF00EE"
//            )
//        )
    }
}