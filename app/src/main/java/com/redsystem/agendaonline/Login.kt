package com.redsystem.agendaonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.redsystem.agendaonline.R
import android.app.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import com.redsystem.agendaonline.Registro
import android.text.TextUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.redsystem.agendaonline.MenuPrincipal
import com.google.android.gms.tasks.OnFailureListener
import androidx.recyclerview.widget.RecyclerView
import com.redsystem.agendaonline.ViewHolder.ViewHolder_Nota.ClickListener
import android.view.View.OnLongClickListener
import com.google.firebase.database.DatabaseReference
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.util.Patterns
import com.google.firebase.database.FirebaseDatabase
import com.redsystem.agendaonline.Objetos.Nota
import android.view.MenuInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.redsystem.agendaonline.ViewHolder.ViewHolder_Nota
import com.firebase.ui.database.FirebaseRecyclerOptions
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.redsystem.agendaonline.Login
import com.google.android.gms.tasks.OnSuccessListener
import com.redsystem.agendaonline.AgregarNota.Agregar_Nota
import com.redsystem.agendaonline.ListarNotas.Listar_Notas
import com.redsystem.agendaonline.NotasArchivadas.Notas_Archivadas
import com.redsystem.agendaonline.Perfil.Perfil_Usuario
import com.redsystem.agendaonline.MainActivity
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class Login : AppCompatActivity() {
    var CorreoLogin: EditText? = null
    var PassLogin: EditText? = null
    var Btn_Logeo: Button? = null
    var UsuarioNuevoTXT: TextView? = null
    var progressDialog: ProgressDialog? = null
    var firebaseAuth: FirebaseAuth? = null

    //Validar los datos
    var correo = ""
    var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionBar = supportActionBar
        actionBar!!.setTitle("Login")
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        CorreoLogin = findViewById(R.id.CorreoLogin)
        PassLogin = findViewById(R.id.PassLogin)
        Btn_Logeo = findViewById(R.id.Btn_Logeo)
        UsuarioNuevoTXT = findViewById(R.id.UsuarioNuevoTXT)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this@Login)
        progressDialog!!.setTitle("Espere por favor")
        progressDialog!!.setCanceledOnTouchOutside(false)
        Btn_Logeo.setOnClickListener(View.OnClickListener { ValidarDatos() })
        UsuarioNuevoTXT.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@Login,
                    Registro::class.java
                )
            )
        })
    }

    private fun ValidarDatos() {
        correo = CorreoLogin!!.text.toString()
        password = PassLogin!!.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show()
        } else {
            LoginDeUsuario()
        }
    }

    private fun LoginDeUsuario() {
        progressDialog!!.setMessage("Iniciando sesión ...")
        progressDialog!!.show()
        firebaseAuth!!.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener(this@Login) { task ->
                if (task.isSuccessful) {
                    progressDialog!!.dismiss()
                    val user = firebaseAuth!!.currentUser
                    startActivity(Intent(this@Login, MenuPrincipal::class.java))
                    Toast.makeText(this@Login, "Bienvenido(a): " + user!!.email, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    progressDialog!!.dismiss()
                    Toast.makeText(
                        this@Login,
                        "Verifique si el correo y contraseña son los correctos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@Login,
                    "" + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}