package global.ututaxfree.androidui

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast

import androidx.appcompat.widget.AppCompatButton

/**
 * Created by Bharath Simha Gupta on 10/24/2019.
 */

class Buttonnn @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.TaxFreeButton
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {

        Toast.makeText(context, "afas", Toast.LENGTH_LONG).show()
    }

    companion object {

        val ASD = "as"
    }
}
