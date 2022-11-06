package com.redsystem.agendaonline

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var Btn_Login: Button
    lateinit var Btn_Registro: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Btn_Login = findViewById(R.id.Btn_Login)
        Btn_Registro = findViewById(R.id.Btn_Registro)
        Btn_Login.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity, Login::class.java
                )
            )
        })
        Btn_Registro.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity, Registro::class.java
                )
            )
        })
    }
}