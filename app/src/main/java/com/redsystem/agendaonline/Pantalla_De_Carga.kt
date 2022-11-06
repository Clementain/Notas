package com.redsystem.agendaonline

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Pantalla_De_Carga : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_de_carga)
        firebaseAuth = FirebaseAuth.getInstance()
        val Tiempo = 3000
        Handler().postDelayed({ /*startActivity(new Intent(Pantalla_De_Carga.this, MainActivity.class));
                finish();*/
            VerificarUsuario()
        }, Tiempo.toLong())
    }

    private fun VerificarUsuario() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this@Pantalla_De_Carga, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@Pantalla_De_Carga, MenuPrincipal::class.java))
            finish()
        }
    }
}