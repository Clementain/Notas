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
import java.util.HashMap

class Registro : AppCompatActivity() {
    var NombreEt: EditText? = null
    var CorreoEt: EditText? = null
    var ContaseñaEt: EditText? = null
    var ConfirmarContraseñaEt: EditText? = null
    var RegistrarUsuario: Button? = null
    var TengounacuentaTXT: TextView? = null
    var firebaseAuth: FirebaseAuth? = null
    var progressDialog: ProgressDialog? = null

    //
    var nombre = " "
    var correo = " "
    var password = ""
    var confirmarpassword = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val actionBar = supportActionBar
        actionBar!!.setTitle("Registrar")
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        NombreEt = findViewById(R.id.NombreEt)
        CorreoEt = findViewById(R.id.CorreoEt)
        ContaseñaEt = findViewById(R.id.ContaseñaEt)
        ConfirmarContraseñaEt = findViewById(R.id.ConfirmarContraseñaEt)
        RegistrarUsuario = findViewById(R.id.RegistrarUsuario)
        TengounacuentaTXT = findViewById(R.id.TengounacuentaTXT)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this@Registro)
        progressDialog!!.setTitle("Espere por favor")
        progressDialog!!.setCanceledOnTouchOutside(false)
        RegistrarUsuario.setOnClickListener(View.OnClickListener { //
            ValidarDatos()
        })
        TengounacuentaTXT.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@Registro,
                    Login::class.java
                )
            )
        })
    }

    private fun ValidarDatos() {
        nombre = NombreEt!!.text.toString()
        correo = CorreoEt!!.text.toString()
        password = ContaseñaEt!!.text.toString()
        confirmarpassword = ConfirmarContraseñaEt!!.text.toString()
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Ingrese correo", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(confirmarpassword)) {
            Toast.makeText(this, "Confirme contraseña", Toast.LENGTH_SHORT).show()
        } else if (password != confirmarpassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        } else {
            CrearCuenta()
        }
    }

    private fun CrearCuenta() {
        progressDialog!!.setMessage("Creando su cuenta...")
        progressDialog!!.show()

        //Crear un usuario en Firebase
        firebaseAuth!!.createUserWithEmailAndPassword(correo, password)
            .addOnSuccessListener { //
                GuardarInformacion()
            }.addOnFailureListener { e ->
                progressDialog!!.dismiss()
                Toast.makeText(this@Registro, "" + e.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun GuardarInformacion() {
        progressDialog!!.setMessage("Guardando su información")
        progressDialog!!.dismiss()

        //Obtener la identificación de usuario actual
        val uid = firebaseAuth!!.uid
        val Datos = HashMap<String, String?>()
        Datos["uid"] = uid
        Datos["correo"] = correo
        Datos["nombres"] = nombre
        Datos["password"] = password
        val databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios")
        databaseReference.child(uid!!)
            .setValue(Datos)
            .addOnSuccessListener {
                progressDialog!!.dismiss()
                Toast.makeText(this@Registro, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@Registro, MenuPrincipal::class.java))
                finish()
            }.addOnFailureListener { e ->
                progressDialog!!.dismiss()
                Toast.makeText(this@Registro, "" + e.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}