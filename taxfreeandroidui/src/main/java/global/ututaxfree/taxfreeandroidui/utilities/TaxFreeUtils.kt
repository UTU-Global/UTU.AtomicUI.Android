package global.ututaxfree.taxfreeandroidui.utilities

import android.content.Context
import android.content.res.Resources

/**
 * Created by Bharath Simha Gupta on 10/24/2019.
 */
class TaxFreeUtils {
    companion object {
        fun PixelsToDp(context: Context, pixels: Int): Float {
            return pixels / (context.resources.displayMetrics.densityDpi / 160f)
        }

        fun pxToDp(px: Int): Int {
            return (px * Resources.getSystem().getDisplayMetrics().density).toInt()
        }
    }
}