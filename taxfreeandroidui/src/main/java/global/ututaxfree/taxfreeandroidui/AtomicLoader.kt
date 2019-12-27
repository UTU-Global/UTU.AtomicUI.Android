package global.ututaxfree.taxfreeandroidui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

/**
 * Created by Bharath Simha Gupta on 11/14/2019.
 */

class AtomicLoader {

    companion object {

        private var dialog: Dialog? = null
        fun show(context: Context?) {
            if (context != null) {
                if (dialog != null && dialog!!.isShowing) {
                    dialog!!.dismiss()
                    dialog = null
                }

                dialog = Dialog(context)
                dialog!!.setContentView(R.layout.dots_loader)
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setCancelable(false)
                dialog!!.setCanceledOnTouchOutside(false)
                dialog!!.show()
            }
        }

        fun close() {
            if (dialog != null) {
                dialog!!.cancel()
                dialog = null
            }
        }
    }
}