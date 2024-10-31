package com.example.trabalhodispmoveis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdicionarActivity : AppCompatActivity() {

    private lateinit var dbHelper: ContatoDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adicionar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = ContatoDbHelper(this)

        val txtNome: EditText = findViewById(R.id.txtNome)
        val txtTelefone: EditText = findViewById(R.id.txtTelefone)
        val btnAdicionarContato: Button = findViewById(R.id.btnAdicionarContato)

        btnAdicionarContato.setOnClickListener {
            val nome = txtNome.text.toString()
            val telefone = txtTelefone.text.toString()

            val contato = Contato(
                id = 0,
                nome = nome,
                telefone = telefone,
            )

            val adicionou = dbHelper.adicionarContato(contato)
            if (adicionou) {
                Toast.makeText(this, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Erro ao salvar o contato", Toast.LENGTH_SHORT).show()
            }
        }
    }
}