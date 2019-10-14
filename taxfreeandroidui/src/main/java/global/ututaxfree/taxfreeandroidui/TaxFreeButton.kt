package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.widget.Toast

/**
 * Created by Bharath Simha Gupta on 10/14/2019.
 */

class TaxFreeButton {

    fun showToast(c: Context, message: String) {
        Toast.makeText(c, message, Toast.LENGTH_LONG).show()
    }
}