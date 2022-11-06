package com.redsystem.agendaonline

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    lateinit var CorreoLogin: EditText
    lateinit var PassLogin: EditText
    lateinit var Btn_Logeo: Button
    lateinit var UsuarioNuevoTXT: TextView
    lateinit var progressDialog: ProgressDialog
    lateinit var firebaseAuth: FirebaseAuth

    //Validar los datos
    var correo = ""
    var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionBar = supportActionBar
        actionBar!!.title = "Login"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        CorreoLogin = findViewById(R.id.CorreoLogin)
        PassLogin = findViewById(R.id.PassLogin)
        Btn_Logeo = findViewById(R.id.Btn_Logeo)
        UsuarioNuevoTXT = findViewById(R.id.UsuarioNuevoTXT)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this@Login)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)
        Btn_Logeo.setOnClickListener(View.OnClickListener { ValidarDatos() })
        UsuarioNuevoTXT.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@Login, Registro::class.java
                )
            )
        })
    }

    private fun ValidarDatos() {
        correo = CorreoLogin.text.toString()
        password = PassLogin.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show()
        } else {
            LoginDeUsuario()
        }
    }

    private fun LoginDeUsuario() {
        progressDialog.setMessage("Iniciando sesión ...")
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener(this@Login) { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    val user = firebaseAuth.currentUser
                    startActivity(Intent(this@Login, MenuPrincipal::class.java))
                    Toast.makeText(this@Login, "Bienvenido(a): " + user!!.email, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(
                        this@Login,
                        "Verifique si el correo y contraseña son los correctos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@Login, "" + e.message, Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}