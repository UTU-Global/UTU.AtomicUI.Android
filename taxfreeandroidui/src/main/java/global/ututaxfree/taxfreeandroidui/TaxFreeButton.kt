package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

/**
 * Created by Bharath Simha Gupta on 10/14/2019.
 */

class TaxFreeButton : AppCompatButton {

    constructor(context: Context) : super(context) {
        onInitialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        onInitialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
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
        setPadding(50,50,50,50)
    }
}