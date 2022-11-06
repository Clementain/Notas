package com.redsystem.agendaonline.ListarNotas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.redsystem.agendaonline.Objetos.Nota
import com.redsystem.agendaonline.R
import com.redsystem.agendaonline.ViewHolder.ViewHolder_Nota
import com.redsystem.agendaonline.ViewHolder.ViewHolder_Nota.ClickListener

class Listar_Notas : AppCompatActivity() {
    lateinit var recyclerviewNotas: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var BASE_DE_DATOS: DatabaseReference
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Nota, ViewHolder_Nota>
    lateinit var options: FirebaseRecyclerOptions<Nota>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_notas)
        val actionBar = supportActionBar
        actionBar!!.title = "Mis notas"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        recyclerviewNotas = findViewById(R.id.recyclerviewNotas)
        recyclerviewNotas.setHasFixedSize(true)
        firebaseDatabase = FirebaseDatabase.getInstance()
        BASE_DE_DATOS = firebaseDatabase.getReference("Notas_Publicadas")
        ListarNotasUsuarios()
    }

    private fun ListarNotasUsuarios() {
        options = FirebaseRecyclerOptions.Builder<Nota>().setQuery(BASE_DE_DATOS, Nota::class.java)
            .build()
        firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Nota, ViewHolder_Nota>(options) {
            override fun onBindViewHolder(
                viewHolder_nota: ViewHolder_Nota, position: Int, nota: Nota
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
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_nota, parent, false)
                val viewHolder_nota = ViewHolder_Nota(view)
                viewHolder_nota.setOnClickListener(object : ClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        Toast.makeText(this@Listar_Notas, "on item click", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        Toast.makeText(
                            this@Listar_Notas, "on item long click", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                return viewHolder_nota
            }
        }
        linearLayoutManager =
            LinearLayoutManager(this@Listar_Notas, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerviewNotas.layoutManager = linearLayoutManager
        recyclerviewNotas.adapter = firebaseRecyclerAdapter
    }

    override fun onStart() {
        super.onStart()
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}