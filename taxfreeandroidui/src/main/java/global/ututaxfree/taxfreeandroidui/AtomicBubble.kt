package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView

class AtomicBubble @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var textView: AppCompatTextView

    init {
        initView()
    }

    private fun initView() {
        val customView = inflate(context, R.layout.atomic_bubble, this)
        textView = customView.findViewById<AppCompatTextView>(R.id.bubbleText)

        if (textView.length() > 2) {
        }

    }


}
