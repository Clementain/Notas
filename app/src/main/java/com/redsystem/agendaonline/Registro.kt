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
import com.google.firebase.database.FirebaseDatabase

class Registro : AppCompatActivity() {
    lateinit var NombreEt: EditText
    lateinit var CorreoEt: EditText
    lateinit var ContasenaEt: EditText
    lateinit var ConfirmarContrasenaEt: EditText
    lateinit var RegistrarUsuario: Button
    lateinit var TengounacuentaTXT: TextView
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var progressDialog: ProgressDialog

    //
    var nombre = " "
    var correo = " "
    var password = ""
    var confirmarpassword = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val actionBar = supportActionBar
        actionBar!!.title = "Registrar"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        NombreEt = findViewById(R.id.NombreEt)
        CorreoEt = findViewById(R.id.CorreoEt)
        ContasenaEt = findViewById(R.id.ContaseñaEt)
        ConfirmarContrasenaEt = findViewById(R.id.ConfirmarContraseñaEt)
        RegistrarUsuario = findViewById(R.id.RegistrarUsuario)
        TengounacuentaTXT = findViewById(R.id.TengounacuentaTXT)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this@Registro)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)
        RegistrarUsuario.setOnClickListener(View.OnClickListener { //
            ValidarDatos()
        })
        TengounacuentaTXT.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@Registro, Login::class.java
                )
            )
        })
    }

    private fun ValidarDatos() {
        nombre = NombreEt.text.toString()
        correo = CorreoEt.text.toString()
        password = ContasenaEt.text.toString()
        confirmarpassword = ConfirmarContrasenaEt.text.toString()
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
        progressDialog.setMessage("Creando su cuenta...")
        progressDialog.show()

        //Crear un usuario en Firebase
        firebaseAuth.createUserWithEmailAndPassword(correo, password).addOnSuccessListener { //
            GuardarInformacion()
        }.addOnFailureListener { e ->
            progressDialog.dismiss()
            Toast.makeText(this@Registro, "" + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun GuardarInformacion() {
        progressDialog.setMessage("Guardando su información")
        progressDialog.dismiss()

        //Obtener la identificación de usuario actual
        val uid = firebaseAuth.uid
        val Datos = HashMap<String, String?>()
        Datos["uid"] = uid
        Datos["correo"] = correo
        Datos["nombres"] = nombre
        Datos["password"] = password
        val databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios")
        databaseReference.child(uid!!).setValue(Datos).addOnSuccessListener {
            progressDialog.dismiss()
            Toast.makeText(this@Registro, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Registro, MenuPrincipal::class.java))
            finish()
        }.addOnFailureListener { e ->
            progressDialog.dismiss()
            Toast.makeText(this@Registro, "" + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}