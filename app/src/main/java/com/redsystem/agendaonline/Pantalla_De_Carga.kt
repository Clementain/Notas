package com.redsystem.agendaonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.redsystem.agendaonline.R
import android.widget.EditText
import android.widget.TextView
import android.app.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import com.redsystem.agendaonline.Registro
import android.widget.Toast
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
import android.os.Handler
import android.widget.DatePicker
import com.google.firebase.database.FirebaseDatabase
import com.redsystem.agendaonline.Objetos.Nota
import android.view.MenuInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.redsystem.agendaonline.ViewHolder.ViewHolder_Nota
import com.firebase.ui.database.FirebaseRecyclerOptions
import android.view.ViewGroup
import android.view.LayoutInflater
import com.redsystem.agendaonline.Login
import com.google.android.gms.tasks.OnSuccessListener
import android.widget.ProgressBar
import com.redsystem.agendaonline.AgregarNota.Agregar_Nota
import com.redsystem.agendaonline.ListarNotas.Listar_Notas
import com.redsystem.agendaonline.NotasArchivadas.Notas_Archivadas
import com.redsystem.agendaonline.Perfil.Perfil_Usuario
import com.redsystem.agendaonline.MainActivity
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class Pantalla_De_Carga : AppCompatActivity() {
    var firebaseAuth: FirebaseAuth? = null
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
        val firebaseUser = firebaseAuth!!.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this@Pantalla_De_Carga, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@Pantalla_De_Carga, MenuPrincipal::class.java))
            finish()
        }
    }
}