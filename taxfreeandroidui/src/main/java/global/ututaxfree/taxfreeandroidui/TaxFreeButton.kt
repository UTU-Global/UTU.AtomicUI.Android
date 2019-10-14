package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

/**
 * Created by Bharath Simha Gupta on 10/14/2019.
 */

class TaxFreeButton(context: Context) : AppCompatButton(context) {

    init {
        onInitialize()
    }

    companion object {
        @JvmStatic
        fun showToast(c: Context, message: String) {
            Toast.makeText(c, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun onInitialize() {
        setBackgroundColor(Color.parseColor("#00b398"))
        setTextColor(Color.parseColor("#FFFFFF"))
    }
}