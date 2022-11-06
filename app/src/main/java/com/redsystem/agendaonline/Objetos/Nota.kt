package com.redsystem.agendaonline.Objetos

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

class Nota {
    //Atributos con los que contará una NOTA
    var id_nota: String? = null
    var uid_usuario: String? = null
    var correo_usuario: String? = null
    var fecha_hora_actual: String? = null
    var titulo: String? = null
    var descripcion: String? = null
    var fecha_nota: String? = null
    var estado: String? = null

    constructor() {}
    constructor(
        id_nota: String?,
        uid_usuario: String?,
        correo_usuario: String?,
        fecha_hora_actual: String?,
        titulo: String?,
        descripcion: String?,
        fecha_nota: String?,
        estado: String?
    ) {
        this.id_nota = id_nota
        this.uid_usuario = uid_usuario
        this.correo_usuario = correo_usuario
        this.fecha_hora_actual = fecha_hora_actual
        this.titulo = titulo
        this.descripcion = descripcion
        this.fecha_nota = fecha_nota
        this.estado = estado
    }
}