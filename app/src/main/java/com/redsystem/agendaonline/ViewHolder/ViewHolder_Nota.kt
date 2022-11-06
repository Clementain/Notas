package com.redsystem.agendaonline.ViewHolder

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
import android.content.Context
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

class ViewHolder_Nota(var mView: View) : RecyclerView.ViewHolder(
    mView
) {
    private var mClickListener: ClickListener? = null

    interface ClickListener {
        fun onItemClick(view: View?, position: Int) /*SE EJECUTA AL PRESIONAR EN EL ITEM*/
        fun onItemLongClick(
            view: View?,
            position: Int
        ) /*SE EJECUTA AL MANTENER PRESIONADO EN EL ITEM*/
    }

    fun setOnClickListener(clickListener: ClickListener?) {
        mClickListener = clickListener
    }

    init {
        itemView.setOnClickListener { view -> mClickListener!!.onItemClick(view, adapterPosition) }
        itemView.setOnLongClickListener { view ->
            mClickListener!!.onItemLongClick(view, adapterPosition)
            false
        }
    }

    fun SetearDatos(
        context: Context?, id_nota: String?, uid_usuario: String?, correo_usuario: String?,
        fecha_hora_registro: String?, titulo: String?, descripcion: String?, fecha_nota: String?,
        estado: String?
    ) {

        //DECLARAR LAS VISTAS
        val Id_nota_Item: TextView
        val Uid_Usuario_Item: TextView
        val Correo_usuario_Item: TextView
        val Fecha_hora_registro_Item: TextView
        val Titulo_Item: TextView
        val Descripcion_Item: TextView
        val Fecha_Item: TextView
        val Estado_Item: TextView

        //ESTABLECER LA CONEXIÓN CON EL ITEM
        Id_nota_Item = mView.findViewById(R.id.Id_nota_Item)
        Uid_Usuario_Item = mView.findViewById(R.id.Uid_Usuario_Item)
        Correo_usuario_Item = mView.findViewById(R.id.Correo_usuario_Item)
        Fecha_hora_registro_Item = mView.findViewById(R.id.Fecha_hora_registro_Item)
        Titulo_Item = mView.findViewById(R.id.Titulo_Item)
        Descripcion_Item = mView.findViewById(R.id.Descripcion_Item)
        Fecha_Item = mView.findViewById(R.id.Fecha_Item)
        Estado_Item = mView.findViewById(R.id.Estado_Item)

        //SETEAR LA INFORMACIÓN DENTRO DEL ITEM
        Id_nota_Item.text = id_nota
        Uid_Usuario_Item.text = uid_usuario
        Correo_usuario_Item.text = correo_usuario
        Fecha_hora_registro_Item.text = fecha_hora_registro
        Titulo_Item.text = titulo
        Descripcion_Item.text = descripcion
        Fecha_Item.text = fecha_nota
        Estado_Item.text = estado
    }
}