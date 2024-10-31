package com.example.trabalhodispmoveis

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContatoDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_contatos.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela
        val createTable = ("CREATE TABLE contatos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "telefone TEXT )")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS contatos")
        onCreate(db)
    }

    fun adicionarContato(contact: Contato): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("nome", contact.nome)
        values.put("telefone", contact.telefone)

        val result = db.insert("contatos", null, values)
        db.close()
        return result != -1L
    }

    fun buscarContatos(): List<Contato> {
        val listaContatos = mutableListOf<Contato>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM contatos ORDER BY nome", null)

        if (cursor.moveToFirst()) {
            do {
                val contact = Contato(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                    telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone")),
                )
                listaContatos.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaContatos
    }
}
