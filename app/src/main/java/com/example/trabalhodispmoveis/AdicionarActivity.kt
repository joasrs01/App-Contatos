package com.example.trabalhodispmoveis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
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
        val containerTelefones: LinearLayout = findViewById(R.id.containerTelefones)
        val btnAdicionarTelefone: Button = findViewById(R.id.btnAdicionarTelefone)
        val btnAdicionarContato: Button = findViewById(R.id.btnAdicionarContato)

        btnAdicionarTelefone.setOnClickListener {
            val telefoneEditText = EditText(this).apply {
                hint = "Telefone"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            containerTelefones.addView(telefoneEditText)
        }

        btnAdicionarContato.setOnClickListener {

            val telefones = mutableListOf<String>()
            for (i in 0 until containerTelefones.childCount) {
                val telefoneEditText = containerTelefones.getChildAt(i) as EditText
                val telefone = telefoneEditText.text.toString()
                if (telefone.isNotBlank()) {
                    telefones.add(telefone)
                }
            }

            val nome = txtNome.text.toString()

            val contato = Contato(
                id = 0,
                nome = nome
            )

            val contatoId = dbHelper.adicionarContato(contato)
            if (contatoId != -1L) {
                telefones.forEach{ telefone ->
                    dbHelper.adicionarTelefone(telefone, contatoId)
                }

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