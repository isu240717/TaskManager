import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class PhoneNumberTextWatcher(private val autoCompleteTextView: TextInputEditText) : TextWatcher {

    private var editing: Boolean = false

    override fun afterTextChanged(s: Editable?) {
        if (editing) return

        editing = true

        val formatted = formatPhoneNumber(s.toString())

        autoCompleteTextView.apply {
            val selectionStart = selectionStart
            val selectionEnd = selectionEnd
            setText(formatted)
            setSelection(getNewCursorPosition(selectionStart, selectionEnd, formatted))
        }

        editing = false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    private fun formatPhoneNumber(phoneNumber: String): String {

        val digits = phoneNumber.replace("\\D+".toRegex(), "")

        val formatted = StringBuilder()
        val prefix = "+996 "
        var i = 0

        if (digits.length >= 3) {
            formatted.append(prefix)
            i += 3
        }

        while (i + 3 <= digits.length) {
            formatted.append(digits.substring(i, i + 3)).append(" ")
            i += 3
        }

        if (i < digits.length) {
            formatted.append(digits.substring(i))
        }

        return formatted.toString()
    }

    private fun getNewCursorPosition(oldStart: Int, oldEnd: Int, newStr: String): Int {
        val prefix = "+996 "
        val newCursorPosition = oldStart + prefix.length

        return when {
            newCursorPosition < 0 -> 0
            newCursorPosition > newStr.length -> newStr.length
            else -> newCursorPosition
        }
    }

    fun getFormattedPhoneNumber(phoneNumber: String): String {
        return formatPhoneNumber(phoneNumber)
    }
}