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

class MenuPrincipal : AppCompatActivity() {
    var AgregarNotas: Button? = null
    var ListarNotas: Button? = null
    var Archivados: Button? = null
    var Perfil: Button? = null
    var AcercaDe: Button? = null
    var CerrarSesion: Button? = null
    var firebaseAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null
    var UidPrincipal: TextView? = null
    var NombresPrincipal: TextView? = null
    var CorreoPrincipal: TextView? = null
    var progressBarDatos: ProgressBar? = null
    var Usuarios: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        val actionBar = supportActionBar
        actionBar!!.setTitle("Agenda Online")
        UidPrincipal = findViewById(R.id.UidPrincipal)
        NombresPrincipal = findViewById(R.id.NombresPrincipal)
        CorreoPrincipal = findViewById(R.id.CorreoPrincipal)
        progressBarDatos = findViewById(R.id.progressBarDatos)
        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios")
        AgregarNotas = findViewById(R.id.AgregarNotas)
        ListarNotas = findViewById(R.id.ListarNotas)
        Archivados = findViewById(R.id.Archivados)
        Perfil = findViewById(R.id.Perfil)
        AcercaDe = findViewById(R.id.AcercaDe)
        CerrarSesion = findViewById(R.id.CerrarSesion)
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth!!.currentUser
        AgregarNotas.setOnClickListener(View.OnClickListener { /*Obtenemos la información de los TextView*/
            val uid_usuario = UidPrincipal.getText().toString()
            val correo_usuario = CorreoPrincipal.getText().toString()

            /*Pasamos datos a la siguiente actividad*/
            val intent = Intent(this@MenuPrincipal, Agregar_Nota::class.java)
            intent.putExtra("Uid", uid_usuario)
            intent.putExtra("Correo", correo_usuario)
            startActivity(intent)
        })
        ListarNotas.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MenuPrincipal, Listar_Notas::class.java))
            Toast.makeText(this@MenuPrincipal, "Listar Notas", Toast.LENGTH_SHORT).show()
        })
        Archivados.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MenuPrincipal, Notas_Archivadas::class.java))
            Toast.makeText(this@MenuPrincipal, "Notas Archivadas", Toast.LENGTH_SHORT).show()
        })
        Perfil.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MenuPrincipal, Perfil_Usuario::class.java))
            Toast.makeText(this@MenuPrincipal, "Perfil Usuario", Toast.LENGTH_SHORT).show()
        })
        AcercaDe.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                this@MenuPrincipal,
                "Acerca De",
                Toast.LENGTH_SHORT
            ).show()
        })
        CerrarSesion.setOnClickListener(View.OnClickListener { SalirAplicacion() })
    }

    override fun onStart() {
        ComprobarInicioSesion()
        super.onStart()
    }

    private fun ComprobarInicioSesion() {
        if (user != null) {
            //El usuario ha iniciado sesión
            CargaDeDatos()
        } else {
            //Lo dirigirá al MainActivity
            startActivity(Intent(this@MenuPrincipal, MainActivity::class.java))
            finish()
        }
    }

    private fun CargaDeDatos() {
        Usuarios!!.child(user!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //Si el usuario existe
                if (snapshot.exists()) {
                    //El progressbar se oculta
                    progressBarDatos!!.visibility = View.GONE
                    //Los TextView se muestran
                    UidPrincipal!!.visibility = View.VISIBLE
                    NombresPrincipal!!.visibility = View.VISIBLE
                    CorreoPrincipal!!.visibility = View.VISIBLE

                    //Obtener los datos
                    val uid = "" + snapshot.child("uid").value
                    val nombres = "" + snapshot.child("nombres").value
                    val correo = "" + snapshot.child("correo").value

                    //Setear los datos en los respectivos TextView
                    UidPrincipal!!.text = uid
                    NombresPrincipal!!.text = nombres
                    CorreoPrincipal!!.text = correo

                    //Habilitar los botones del menú
                    AgregarNotas!!.isEnabled = true
                    ListarNotas!!.isEnabled = true
                    Archivados!!.isEnabled = true
                    Perfil!!.isEnabled = true
                    AcercaDe!!.isEnabled = true
                    CerrarSesion!!.isEnabled = true
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun SalirAplicacion() {
        firebaseAuth!!.signOut()
        startActivity(Intent(this@MenuPrincipal, MainActivity::class.java))
        Toast.makeText(this, "Cerraste sesión exitosamente", Toast.LENGTH_SHORT).show()
    }
}