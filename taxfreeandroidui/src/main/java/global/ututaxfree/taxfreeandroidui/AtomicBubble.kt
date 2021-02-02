package global.ututaxfree.taxfreeandroidui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import global.ututaxfree.taxfreeandroidui.utilities.TaxFreeUtils

@SuppressLint("ViewConstructor")
class AtomicBubble : AppCompatTextView {

    private var value: String? = null

    constructor(context: Context, value: String) : super(context) {
        onInit(context, null, R.style.AtomicBubble, value)
    }

    constructor(
        context: Context, attrs: AttributeSet
    ) : super(context, attrs) {
        onInit(context, attrs, R.style.AtomicBubble, null)
    }

    constructor(
        context: Context, attrs: AttributeSet, defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        onInit(context, attrs, defStyleAttr, null)
    }

    private fun onInit(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int,
        bubbleValue: String?
    ) {
        if (!TextUtils.isEmpty(bubbleValue)) {
            value = bubbleValue
        } else {
            val atr = context.obtainStyledAttributes(
                attrs, R.styleable.AtomicBubble, defStyleAttr, R.style.AtomicBubble
            )
            value = atr.getString(R.styleable.AtomicBubble_value)
            atr.recycle()
        }
        width = TaxFreeUtils.pxToDp(32)
        height = TaxFreeUtils.pxToDp(32)
        maxLines = 1
        background = ContextCompat.getDrawable(
            context,
            R.drawable.notification_bubble_bg
        )
        setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        textSize = 14.toFloat()
        typeface = ResourcesCompat.getFont(context, R.font.notosans_bold)
        gravity = Gravity.CENTER

        onBuildBubble()
    }

    private fun onBuildBubble() {
        if (!TextUtils.isEmpty(value) && !value.equals("empty")) {
            if (value!!.length > 2) {
                value = "99+"
            }
            text = value
        }
    }

    fun setValue(bubbleValue: String) {
        value = bubbleValue
        onBuildBubble()
    }
}
