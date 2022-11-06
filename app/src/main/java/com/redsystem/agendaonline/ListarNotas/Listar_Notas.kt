package com.redsystem.agendaonline.ListarNotas

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
import android.view.View
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

class Listar_Notas : AppCompatActivity() {
    var recyclerviewNotas: RecyclerView? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var BASE_DE_DATOS: DatabaseReference? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Nota, ViewHolder_Nota>? = null
    var options: FirebaseRecyclerOptions<Nota>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_notas)
        val actionBar = supportActionBar
        actionBar!!.setTitle("Mis notas")
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        recyclerviewNotas = findViewById(R.id.recyclerviewNotas)
        recyclerviewNotas.setHasFixedSize(true)
        firebaseDatabase = FirebaseDatabase.getInstance()
        BASE_DE_DATOS = firebaseDatabase!!.getReference("Notas_Publicadas")
        ListarNotasUsuarios()
    }

    private fun ListarNotasUsuarios() {
        options =
            FirebaseRecyclerOptions.Builder<Nota>().setQuery(BASE_DE_DATOS!!, Nota::class.java)
                .build()
        firebaseRecyclerAdapter =
            object : FirebaseRecyclerAdapter<Nota, ViewHolder_Nota>(options!!) {
                override fun onBindViewHolder(
                    viewHolder_nota: ViewHolder_Nota,
                    position: Int,
                    nota: Nota
                ) {
                    viewHolder_nota.SetearDatos(
                        applicationContext,
                        nota.id_nota,
                        nota.uid_usuario,
                        nota.correo_usuario,
                        nota.fecha_hora_actual,
                        nota.titulo,
                        nota.descripcion,
                        nota.fecha_nota,
                        nota.estado
                    )
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Nota {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_nota, parent, false)
                    val viewHolder_nota = ViewHolder_Nota(view)
                    viewHolder_nota.setOnClickListener(object : ClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            Toast.makeText(this@Listar_Notas, "on item click", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onItemLongClick(view: View?, position: Int) {
                            Toast.makeText(
                                this@Listar_Notas,
                                "on item long click",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                    return viewHolder_nota
                }
            }
        linearLayoutManager =
            LinearLayoutManager(this@Listar_Notas, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager!!.reverseLayout = true
        linearLayoutManager!!.stackFromEnd = true
        recyclerviewNotas!!.layoutManager = linearLayoutManager
        recyclerviewNotas!!.adapter = firebaseRecyclerAdapter
    }

    override fun onStart() {
        super.onStart()
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter!!.startListening()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}