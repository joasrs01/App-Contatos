import android.icu.text.ListFormatter.Width
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalhodispmoveis.Contato
import com.example.trabalhodispmoveis.R

class AdaptadorContato(private val listaContatos: List<Contato>) :
    RecyclerView.Adapter<AdaptadorContato.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contato = listaContatos[position]
        holder.bind(contato)
    }

    override fun getItemCount() = listaContatos.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.textViewNome)
        val containerTelefones: LinearLayout = itemView.findViewById(R.id.containerTelefones)

        fun bind(contato: Contato) {
            textViewNome.text = contato.nome
            containerTelefones.removeAllViews()

            contato.telefones.forEach { telefone ->
                val telefoneTextView = TextView(itemView.context).apply {
                    text = telefone
                    width = 20
                }
                containerTelefones.addView(telefoneTextView)
            }
        }
    }
}
