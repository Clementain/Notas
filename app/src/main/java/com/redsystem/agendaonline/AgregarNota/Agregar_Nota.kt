package com.redsystem.agendaonline.AgregarNota

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
import android.view.*
import com.google.firebase.database.FirebaseDatabase
import com.redsystem.agendaonline.Objetos.Nota
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.redsystem.agendaonline.ViewHolder.ViewHolder_Nota
import com.firebase.ui.database.FirebaseRecyclerOptions
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
import java.text.SimpleDateFormat
import java.util.*

class Agregar_Nota private constructor() : AppCompatActivity() {
    var Uid_Usuario: TextView? = null
    var Correo_usuario: TextView? = null
    var Fecha_hora_actual: TextView? = null
    var Fecha: TextView? = null
    var Estado: TextView? = null
    var Titulo: EditText? = null
    var Descripcion: EditText? = null
    var Btn_Calendario: Button? = null
    var dia = 0
    var mes = 0
    var anio = 0
    var BD_Firebase: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)
        val actionBar = supportActionBar
        actionBar!!.title = ""
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        InicializarVariables()
        ObtenerDatos()
        Obtener_Fecha_Hora_Actual()
        Btn_Calendario!!.setOnClickListener {
            val calendario = Calendar.getInstance()
            dia = calendario[Calendar.DAY_OF_MONTH]
            mes = calendario[Calendar.MONTH]
            anio = calendario[Calendar.YEAR]
            val datePickerDialog = DatePickerDialog(
                this@Agregar_Nota,
                { datePicker, AnioSeleccionado, MesSeleccionado, DiaSeleccionado ->
                    val diaFormateado: String
                    val mesFormateado: String

                    //OBTENER DIA
                    diaFormateado = if (DiaSeleccionado < 10) {
                        "0$DiaSeleccionado"
                        // Antes: 9/11/2022 -  Ahora 09/11/2022
                    } else {
                        DiaSeleccionado.toString()
                        //Ejemplo 13/08/2022
                    }

                    //OBTENER EL MES
                    val Mes = MesSeleccionado + 1
                    mesFormateado = if (Mes < 10) {
                        "0$Mes"
                        // Antes: 09/8/2022 -  Ahora 09/08/2022
                    } else {
                        Mes.toString()
                        //Ejemplo 13/10/2022 - 13/11/2022 - 13/12/2022
                    }

                    //Setear fecha en TextView
                    Fecha!!.text = "$diaFormateado/$mesFormateado/$AnioSeleccionado"
                },
                anio,
                mes,
                dia)
            datePickerDialog.show()
        }
    }

    private fun InicializarVariables() {
        Uid_Usuario = findViewById(R.id.Uid_Usuario)
        Correo_usuario = findViewById(R.id.Correo_usuario)
        Fecha_hora_actual = findViewById(R.id.Fecha_hora_actual)
        Fecha = findViewById(R.id.Fecha)
        Estado = findViewById(R.id.Estado)
        Titulo = findViewById(R.id.Titulo)
        Descripcion = findViewById(R.id.Descripcion)
        Btn_Calendario = findViewById(R.id.Btn_Calendario)
        BD_Firebase = FirebaseDatabase.getInstance().reference
    }

    private fun ObtenerDatos() {
        val uid_recuperado = intent.getStringExtra("Uid")
        val correo_recuperado = intent.getStringExtra("Correo")
        Uid_Usuario!!.text = uid_recuperado
        Correo_usuario!!.text = correo_recuperado
    }

    private fun Obtener_Fecha_Hora_Actual() {
        val Fecha_hora_registro = SimpleDateFormat(
            "dd-MM-yyyy/HH:mm:ss a",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        //EJEMPLO: 13-11-2022/06:30:20 pm
        Fecha_hora_actual!!.text = Fecha_hora_registro
    }

    init {

        //Obtener los datos
        val uid_usuario = Uid_Usuario!!.text.toString()
        val correo_usuario = Correo_usuario!!.text.toString()
        val fecha_hora_actual = Fecha_hora_actual!!.text.toString()
        val titulo = Titulo!!.text.toString()
        val descripcion = Descripcion!!.text.toString()
        val fecha = Fecha!!.text.toString()
        val estado = Estado!!.text.toString()

        //Validar datos
        if (uid_usuario != "" && correo_usuario != "" && fecha_hora_actual != "" &&
            titulo != "" && descripcion != "" && fecha != "" && estado != ""
        ) {
            val nota = Nota(
                "$correo_usuario/$fecha_hora_actual",
                uid_usuario,
                correo_usuario,
                fecha_hora_actual,
                titulo,
                descripcion,
                fecha,
                estado
            )
            val Nota_usuario = BD_Firebase!!.push().key
            //Establecer el nombre de la BD
            val Nombre_BD = "Notas_Publicadas"
            BD_Firebase!!.child(Nombre_BD).child(Nota_usuario!!).setValue(nota)
            Toast.makeText(this, "Se ha agregado la nota exitosamente", Toast.LENGTH_SHORT).show()
            onBackPressed()
        } else {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_agenda, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Agregar_Nota_BD -> Agregar_Nota()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}