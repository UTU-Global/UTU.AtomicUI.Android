package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.androidadvance.topsnackbar.TSnackbar
import global.ututaxfree.taxfreeandroidui.utilities.TaxFreeUtils

/**
 * Created by Bharath Simha Gupta on 10/25/2019.
 */

class AtomicToast {

    companion object {

        const val TYPE_SUCCESS = "small"
        const val TYPE_WARNING = "regular"
        const val TYPE_ERROR = "big"

        fun show(
            context: Context, view: View, actionText: String, actionType: String,
            listener: ToastClosedListener?
        ) {
            val snackBar: TSnackbar = TSnackbar.make(view, "", TSnackbar.LENGTH_LONG)
            val customView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                    .inflate(R.layout.utu_toast, null)
            val snackBarView = snackBar.view as TSnackbar.SnackbarLayout
            val parentParams = snackBarView.layoutParams as FrameLayout.LayoutParams
            parentParams.width = FrameLayout.LayoutParams.MATCH_PARENT
            parentParams.height = TaxFreeUtils.pxToDp(48)
            parentParams.gravity = Gravity.TOP
            snackBarView.layoutParams = parentParams

            val textView = customView.findViewById<AppCompatTextView>(R.id.toastText)
            textView.text = actionText

            val closeToast = customView.findViewById<ImageButton>(R.id.closeToast)

            when (actionType) {
                TYPE_SUCCESS -> {
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_success, 0, 0, 0
                    )
                    closeToast.setImageResource(R.drawable.ic_success_close)
                }
                TYPE_WARNING -> {
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_warning, 0, 0, 0
                    )
                    closeToast.setImageResource(R.drawable.ic_warning_close)
                }
                TYPE_ERROR -> {
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_error, 0, 0, 0
                    )
                    closeToast.setImageResource(R.drawable.ic_error_close)
                }
            }

            closeToast.setOnClickListener {
                snackBar.dismiss()
                listener?.onToastClosed()
            }

            snackBarView.setPadding(0, 0, 0, 0)
            snackBarView.addView(customView, 0)
            snackBar.show()
        }
    }
}