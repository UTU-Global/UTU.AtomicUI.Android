package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.os.Handler
import android.text.Layout
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.androidadvance.topsnackbar.TSnackbar

/**
 * Created by Bharath Simha Gupta on 10/25/2019.
 */

class AtomicToast {

    constructor(
        context: Context,
        view: View,
        actionText: String,
        actionType: String,
        listener: ToastClosedListener?,
        isIndefinite: Boolean
    ) {
        this.context = context
        this.mView = view
        this.actionText = actionText
        this.actionType = actionType
        this.listener = listener
        this.isIndefinite = isIndefinite
        onBuildToast()
    }

    constructor(
        context: Context,
        view: View,
        actionText: String,
        actionType: String,
        listener: ToastClosedListener?,
        isIndefinite: Boolean,
        isFullScreen: Boolean
    ) {
        this.context = context
        this.mView = view
        this.actionText = actionText
        this.actionType = actionType
        this.listener = listener
        this.isIndefinite = isIndefinite
        this.isFullScreen = isFullScreen
        onBuildToast()
    }

    constructor(
        context: Context,
        view: View,
        actionText: String,
        actionType: String,
        listener: ToastClosedListener?
    ) {
        this.context = context
        this.mView = view
        this.actionText = actionText
        this.actionType = actionType
        this.listener = listener
        onBuildToast()
    }

    private var context: Context
    private var mView: View
    private var actionText: String
    private var actionType: String
    private var listener: ToastClosedListener?
    private var isIndefinite: Boolean = false
    private var isFullScreen: Boolean = false

    private var isEllipsized = false
    private var snackBar: TSnackbar? = null

    private var handler: Handler? = null

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId: Int =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun onBuildToast() {
        if (!isIndefinite) {
            handler = Handler()
        }
        snackBar = TSnackbar.make(mView, "", TSnackbar.LENGTH_INDEFINITE)
        val customView =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.utu_toast, null)
        val snackBarView = snackBar!!.view as TSnackbar.SnackbarLayout
        val parentParams = snackBarView.layoutParams as ViewGroup.LayoutParams
        parentParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        parentParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        if (parentParams is FrameLayout.LayoutParams) {
            parentParams.gravity = Gravity.TOP
            if (isFullScreen) {
                parentParams.topMargin = getStatusBarHeight()
            }
        }
        snackBarView.layoutParams = parentParams

        val textView = customView.findViewById<AppCompatTextView>(R.id.toastText)
        textView.text = actionText

        textView.setOnClickListener {
            if (isEllipsized) {
                if (!isIndefinite) {
                    handler?.removeCallbacksAndMessages(null)
                }
                textView.maxLines = Int.MAX_VALUE
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
            if (!isIndefinite) {
                handler?.removeCallbacksAndMessages(null)
            }
            if (snackBar != null) {
                snackBar!!.dismiss()
            }
        }

        snackBar!!.setCallback(object : TSnackbar.Callback() {
            override fun onDismissed(snackbar: TSnackbar?, event: Int) {
                super.onDismissed(snackbar, event)
                if (!isIndefinite) {
                    handler?.removeCallbacksAndMessages(null)
                }
                listener?.onToastClosed()
            }
        })

        snackBarView.setPadding(0, 0, 0, 0)
        snackBarView.addView(customView, 0)

        val vto: ViewTreeObserver = textView.viewTreeObserver
        vto.addOnGlobalLayoutListener {
            if (textView.layout != null) {
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
            if (!isIndefinite) {
                handler?.postDelayed({
                    if (snackBar != null) {
                        snackBar!!.dismiss()
                    }
                }, 3000)
            }
        }
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