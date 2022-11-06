package com.redsystem.agendaonline.ViewHolder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redsystem.agendaonline.R

class ViewHolder_Nota(var mView: View) : RecyclerView.ViewHolder(
    mView
) {
    private lateinit var mClickListener: ClickListener

    interface ClickListener {
        fun onItemClick(view: View?, position: Int) /*SE EJECUTA AL PRESIONAR EN EL ITEM*/
        fun onItemLongClick(
            view: View?, position: Int
        ) /*SE EJECUTA AL MANTENER PRESIONADO EN EL ITEM*/
    }

    fun setOnClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }

    init {
        itemView.setOnClickListener { view -> mClickListener.onItemClick(view, adapterPosition) }
        itemView.setOnLongClickListener { view ->
            mClickListener.onItemLongClick(view, adapterPosition)
            false
        }
    }

    fun SetearDatos(
        context: Context?,
        id_nota: String?,
        uid_usuario: String?,
        correo_usuario: String?,
        fecha_hora_registro: String?,
        titulo: String?,
        descripcion: String?,
        fecha_nota: String?,
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