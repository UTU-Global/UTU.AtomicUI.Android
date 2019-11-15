package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import global.ututaxfree.taxfreeandroidui.utilities.TaxFreeUtils

/**
 * Created by Bharath Simha Gupta on 10/14/2019.
 */

class TaxFreeButton : AppCompatButton {

    constructor(context: Context) : super(context) {
        onInit(context, null, R.style.TaxFreeButton)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        onInit(context, attrs, R.style.TaxFreeButton)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        onInit(context, attrs, defStyleAttr)
    }

    private var isDisabled = false
    private var isOutlined = false

    public fun setOutlined(outlined: Boolean) {
        isOutlined = outlined
        onBuildTaxFreeButton()
    }

    public fun setDisabled(disabled: Boolean) {
        isDisabled = disabled
        onBuildTaxFreeButton()
    }

    private fun onInit(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        gravity = Gravity.CENTER
        isAllCaps = false
        typeface = ResourcesCompat.getFont(context, R.font.notosans_bold)

        val atr = context.obtainStyledAttributes(
            attrs, R.styleable.TaxFreeButton, defStyleAttr, R.style.TaxFreeButton
        )

        isOutlined = atr.getBoolean(R.styleable.TaxFreeButton_outlined, false)
        isDisabled = atr.getBoolean(R.styleable.TaxFreeButton_disabled, false)
        onBuildTaxFreeButton()

        when (atr.getString(R.styleable.TaxFreeButton_size)) {
            SIZE_SMALL -> {
                width = TaxFreeUtils.pxToDp(108)
                height = TaxFreeUtils.pxToDp(32)
                textSize = 14F
            }
            SIZE_REGULAR -> {
                width = TaxFreeUtils.pxToDp(164)
                height = TaxFreeUtils.pxToDp(40)
                textSize = 14F
            }
            SIZE_BIG -> {
                width = TaxFreeUtils.pxToDp(186)
                height = TaxFreeUtils.pxToDp(48)
                textSize = 16F
            }
        }

        atr.recycle()
    }

    private fun onBuildTaxFreeButton() {
        when (isOutlined) {
            true -> {
                // Disabled button
                when (isDisabled) {
                    // Outlined disabled button
                    true -> {
                        background =
                            ContextCompat.getDrawable(context, R.drawable.outlined_disabled)

                        setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.disabledTextOutlinedButtonColor
                            )
                        )
                    }
                    false -> {
                        background =
                            ContextCompat.getDrawable(context, R.drawable.outlined_state)
                        setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.enabledTextOutlinedButtonColor
                            )
                        )
                    }
                }
            }
            false -> {
                when (isDisabled) {
                    true -> {
                        background =
                            ContextCompat.getDrawable(context, R.drawable.combined_disabled)
                        setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.disabledTextButtonColor
                            )
                        )
                    }
                    false -> {
                        background =
                            ContextCompat.getDrawable(context, R.drawable.combined_state)
                        setTextColor(
                            ContextCompat.getColor(
                                context, R.color.enabledTextButtonColor
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val SIZE_SMALL = "small"
        const val SIZE_REGULAR = "regular"
        const val SIZE_BIG = "big"
    }
}
