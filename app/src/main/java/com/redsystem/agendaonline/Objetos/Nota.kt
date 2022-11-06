package com.redsystem.agendaonline.Objetos

class Nota {
    //Atributos con los que contará una NOTA
    lateinit var id_nota: String
    lateinit var uid_usuario: String
    lateinit var correo_usuario: String
    lateinit var fecha_hora_actual: String
    lateinit var titulo: String
    lateinit var descripcion: String
    lateinit var fecha_nota: String
    lateinit var estado: String

    constructor()
    constructor(
        id_nota: String,
        uid_usuario: String,
        correo_usuario: String,
        fecha_hora_actual: String,
        titulo: String,
        descripcion: String,
        fecha_nota: String,
        estado: String
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