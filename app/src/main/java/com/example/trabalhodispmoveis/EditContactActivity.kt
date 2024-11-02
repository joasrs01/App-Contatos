/*
class EditContactActivity : AppCompatActivity() {

    private lateinit var etContactName: EditText
    private lateinit var etContactEmail: EditText
    private lateinit var phoneNumbersContainer: LinearLayout
    private lateinit var btnAddPhoneNumber: Button
    private lateinit var btnSaveChanges: Button

    private var contactId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_contact)

        etContactName = findViewById(R.id.etContactName)
        etContactEmail = findViewById(R.id.etContactEmail)
        phoneNumbersContainer = findViewById(R.id.phoneNumbersContainer)
        btnAddPhoneNumber = findViewById(R.id.btnAddPhoneNumber)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)

        // Receber o ID do contato da intent
        contactId = intent.getLongExtra("CONTACT_ID", -1)

        // Carregar dados do contato para edição
        loadContactData()

        // Adicionar número de telefone
        btnAddPhoneNumber.setOnClickListener {
            addPhoneNumberField(null)
        }

        // Salvar alterações no contato
        btnSaveChanges.setOnClickListener {
            saveChanges()
        }
    }

    private fun loadContactData() {
        // Obter os dados do contato no banco de dados (exemplo)
        val db = DatabaseHelper(this)
        val contact = db.getContactById(contactId)

        // Definir os dados carregados nos campos de edição
        etContactName.setText(contact?.name)
        etContactEmail.setText(contact?.email)

        // Carregar e adicionar os números de telefone
        contact?.phoneNumbers?.forEach { phoneNumber ->
            addPhoneNumberField(phoneNumber)
        }
    }

    private fun addPhoneNumberField(phoneNumber: PhoneNumber?) {
        // Layout do campo para cada número de telefone
        val phoneView = layoutInflater.inflate(R.layout.phone_number_item, phoneNumbersContainer, false)

        val etPhoneNumber = phoneView.findViewById<EditText>(R.id.etPhoneNumber)
        val spinnerPhoneType = phoneView.findViewById<Spinner>(R.id.spinnerPhoneType)

        // Opções de tipo de número
        val phoneTypes = listOf("Casa", "Celular", "Trabalho")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, phoneTypes)
        spinnerPhoneType.adapter = adapter

        // Preencher dados se existir
        phoneNumber?.let {
            etPhoneNumber.setText(it.number)
            spinnerPhoneType.setSelection(phoneTypes.indexOf(it.type))
        }

        phoneNumbersContainer.addView(phoneView)
    }

    private fun saveChanges() {
        // Salvar os dados do contato atualizado
        val name = etContactName.text.toString()
        val email = etContactEmail.text.toString()

        // Obter todos os números de telefone
        val phoneNumbers = mutableListOf<PhoneNumber>()
        for (i in 0 until phoneNumbersContainer.childCount) {
            val phoneView = phoneNumbersContainer.getChildAt(i)
            val etPhoneNumber = phoneView.findViewById<EditText>(R.id.etPhoneNumber)
            val spinnerPhoneType = phoneView.findViewById<Spinner>(R.id.spinnerPhoneType)

            val number = etPhoneNumber.text.toString()
            val type = spinnerPhoneType.selectedItem.toString()
            phoneNumbers.add(PhoneNumber(number, type))
        }

        // Atualizar no banco de dados
        val db = DatabaseHelper(this)
        db.updateContact(contactId, name, email, phoneNumbers)

        // Encerrar a atividade
        finish()
    }
}
