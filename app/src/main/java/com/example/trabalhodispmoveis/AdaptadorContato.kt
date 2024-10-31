import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val contact = listaContatos[position]
        holder.textViewNome.text = contact.nome
        holder.textViewTelefone.text = contact.telefone
    }

    override fun getItemCount() = listaContatos.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.textViewNome)
        val textViewTelefone: TextView = itemView.findViewById(R.id.textViewTelefone)
    }
}
