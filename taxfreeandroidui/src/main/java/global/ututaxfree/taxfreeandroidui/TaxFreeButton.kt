package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import global.ututaxfree.taxfreeandroidui.utilities.TaxFreeUtils

/**
 * Created by Bharath Simha Gupta on 10/14/2019.
 */

class TaxFreeButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.TaxFreeButton
) : AppCompatButton(context, attrs, defStyleAttr) {
    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.TaxFreeButton, defStyleAttr, R.style.TaxFreeButton
        )
        if (a.hasValue(R.styleable.TaxFreeButton_size)) {
            when (a.getString(R.styleable.TaxFreeButton_size)) {
                SIZE_SMALL -> {
                    width = TaxFreeUtils.pxToDp(108)
                    height = TaxFreeUtils.pxToDp(32)
                }
                SIZE_REGULAR -> {
                    width = TaxFreeUtils.pxToDp(164)
                    height = TaxFreeUtils.pxToDp(40)
                }
                SIZE_BIG -> {
                    width = TaxFreeUtils.pxToDp(186)
                    height = TaxFreeUtils.pxToDp(48)
                }
            }
        }
        a.recycle()
    }

    companion object {

        const val SIZE_SMALL = "small"
        const val SIZE_REGULAR = "regular"
        const val SIZE_BIG = "big"

        @JvmStatic
        fun showToast(c: Context, message: String) {
            Toast.makeText(c, message, Toast.LENGTH_LONG).show()
        }
    }
}