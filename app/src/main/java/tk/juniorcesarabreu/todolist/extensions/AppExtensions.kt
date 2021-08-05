package tk.juniorcesarabreu.todolist.extensions

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt", "BR")

fun Date.format(): String {
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}

// cria uma extensão de um objeto
var TextInputLayout.text: String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }