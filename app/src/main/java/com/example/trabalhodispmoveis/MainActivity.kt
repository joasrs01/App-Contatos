package com.example.trabalhodispmoveis

import AdaptadorContato
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rcView: RecyclerView
    private lateinit var adaptadorContato: AdaptadorContato
    private lateinit var dbHelper: ContatoDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = ContatoDbHelper(this)

        val btnAdicionarContato: Button = findViewById(R.id.btnAdicionarContato)

        btnAdicionarContato.setOnClickListener {
            val intent = Intent(this, AdicionarActivity::class.java)
            startActivity(intent)
        }

        rcView = findViewById(R.id.rcView)
        rcView.layoutManager = LinearLayoutManager(this)

        val listaContatos = dbHelper.buscarContatos()

        if(listaContatos.size > 0){
            listaContatos.forEach{ contato -> contato.telefones = dbHelper.buscarTelefonesPorContato(contato.id) }
        }

        adaptadorContato = AdaptadorContato(listaContatos)
        rcView.adapter = adaptadorContato
    }
}