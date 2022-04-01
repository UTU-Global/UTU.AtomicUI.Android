package global.ututaxfree.taxfreeandroidui.utilities

import android.content.res.Resources

/**
 * Created by Bharath Simha Gupta on 10/24/2019.
 */
class TaxFreeUtils {
    companion object {
        fun pxToDp(px: Int): Int {
            return (px * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}