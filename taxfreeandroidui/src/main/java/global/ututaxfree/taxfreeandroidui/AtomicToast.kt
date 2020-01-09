package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.os.Handler
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.androidadvance.topsnackbar.TSnackbar

/**
 * Created by Bharath Simha Gupta on 10/25/2019.
 */

class AtomicToast(
    private var context: Context,
    private var view: View,
    private var actionText: String,
    private var actionType: String,
    private var listener: ToastClosedListener?
) {

    private var isEllipsized = false
    private var snackBar: TSnackbar? = null

    init {
        onBuildToast()
    }

    private fun onBuildToast() {
        val handler = Handler()
        snackBar = TSnackbar.make(view, "", TSnackbar.LENGTH_INDEFINITE)
        val customView =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.utu_toast, null)
        val snackBarView = snackBar!!.view as TSnackbar.SnackbarLayout
        val parentParams = snackBarView.layoutParams as FrameLayout.LayoutParams
        parentParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        parentParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
        parentParams.gravity = Gravity.TOP
        snackBarView.layoutParams = parentParams

        val textView = customView.findViewById<AppCompatTextView>(R.id.toastText)
        textView.text = actionText

        textView.setOnClickListener {
            if (isEllipsized) {
                handler.removeCallbacksAndMessages(null)
                textView.maxLines = 4
            }
        }

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
            handler.removeCallbacksAndMessages(null)
            if (snackBar != null) {
                snackBar!!.dismiss()
            }
        }

        snackBar!!.setCallback(object : TSnackbar.Callback() {
            override fun onDismissed(snackbar: TSnackbar?, event: Int) {
                super.onDismissed(snackbar, event)
                handler.removeCallbacksAndMessages(null)
                listener?.onToastClosed()
            }
        })

        snackBarView.setPadding(0, 0, 0, 0)
        snackBarView.addView(customView, 0)

        val vto: ViewTreeObserver = textView.viewTreeObserver
        vto.addOnGlobalLayoutListener {
            val layout: Layout = textView.layout
            val lines = layout.lineCount
            if (lines > 0) {
                if (layout.getEllipsisCount
                        (lines - 1) > 0
                ) {
                    isEllipsized = true
                }
            }
        }
        handler.postDelayed({
            if (snackBar != null) {
                snackBar!!.dismiss()
            }
        }, 2500)
    }

    fun show() {
        if (snackBar != null) {
            snackBar!!.show()
        }
    }

    fun dismiss() {
        if (snackBar != null) {
            snackBar!!.dismiss()
        }
    }

    companion object {
        const val TYPE_SUCCESS = "small"
        const val TYPE_WARNING = "regular"
        const val TYPE_ERROR = "big"
    }
}