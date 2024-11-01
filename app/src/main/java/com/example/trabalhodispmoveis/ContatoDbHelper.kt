package com.example.trabalhodispmoveis

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContatoDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_contatos.db"
        private const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela
        val createTableContatos = ("CREATE TABLE contatos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT )")
        val createTableTelefones = ("CREATE TABLE telefones (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "telefone TEXT," +
                "contato_id INTEGER," +
                "FOREIGN KEY (contato_id) REFERENCES contatos(id) " +
                "ON DELETE CASCADE )")
        db.execSQL(createTableContatos)
        db.execSQL(createTableTelefones)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS contatos")
        db.execSQL("DROP TABLE IF EXISTS telefones")
        onCreate(db)
    }

    fun adicionarContato(contact: Contato): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("nome", contact.nome)

        val result = db.insert("contatos", null, values)
        db.close()
        return result
    }

    fun adicionarTelefone(telefone:String, contatoId: Long): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("telefone", telefone)
        values.put("contato_id", contatoId)

        val result = db.insert("telefones", null, values)
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
                    nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                )
                listaContatos.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaContatos
    }

    fun buscarTelefonesPorContato(contactId: Int): List<String> {
        val telefones = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM telefones WHERE contato_id = $contactId", null)

        if (cursor.moveToFirst()) {
            do {
                val telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))
                telefones.add(telefone)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return telefones
    }
}
