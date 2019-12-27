package global.ututaxfree.taxfreeandroidui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText

/**
 * Created by Likhitha Kolla on 18/12/2019.
 */

class AtomicTextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) :
    TextInputEditText(context, attrs, defStyleAttr), TextWatcher {

    private var mClearIconDrawable: Drawable? = null
    private var mIsClearIconShown = false
    private var mClearIconDrawWhenFocused = true

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) { // Load attributes

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.AtomicTextInput, defStyle, 0
        )
        mClearIconDrawable = if (a.hasValue(R.styleable.AtomicTextInput_clearIconDrawable)) {
            a.getDrawable(R.styleable.AtomicTextInput_clearIconDrawable)
        } else {
            ContextCompat.getDrawable(
                context,
                DEFAULT_CLEAR_ICON_RES_ID
            )
        }
        if (mClearIconDrawable != null) {
            mClearIconDrawable!!.callback = this
        }
        mClearIconDrawWhenFocused =
            a.getBoolean(R.styleable.AtomicTextInput_clearIconDrawWhenFocused, true)
        a.recycle()
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}
    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return if (mIsClearIconShown) ClearIconSavedState(
            superState,
            true
        ) else superState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is ClearIconSavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        mIsClearIconShown = state.isClearIconShown
        showClearIcon(mIsClearIconShown)
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (!mClearIconDrawWhenFocused || hasFocus()) {
            showClearIcon(!TextUtils.isEmpty(s))
        }
    }

    override fun onFocusChanged(
        focused: Boolean,
        direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        showClearIcon((!mClearIconDrawWhenFocused || focused) && !TextUtils.isEmpty(text))
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isClearIconTouched(event)) {
            text = null
            event.action = MotionEvent.ACTION_CANCEL
            showClearIcon(false)
            return false
        }
        return super.onTouchEvent(event)
    }

    private fun isClearIconTouched(event: MotionEvent): Boolean {
        if (!mIsClearIconShown) {
            return false
        }
        val touchPointX = event.x.toInt()
        val widthOfView = width
        val compoundPadding =
            if (Build.VERSION.SDK_INT >= 23) compoundPaddingEnd else compoundPaddingRight
        return touchPointX >= widthOfView - compoundPadding
    }

    private fun showClearIcon(show: Boolean) {
        if (Build.VERSION.SDK_INT >= 23) {
            val drawables = compoundDrawablesRelative
            if (show) { // show icon on the right
                setCompoundDrawablesRelativeWithIntrinsicBounds(
                    drawables[0], drawables[1], mClearIconDrawable, drawables[3]
                )
            } else { // remove icon
                setCompoundDrawablesRelative(
                    drawables[0],
                    drawables[1],
                    null,
                    drawables[3]
                )
            }
        } else {
            val drawables = compoundDrawables
            if (show) { // show icon on the right
                setCompoundDrawablesWithIntrinsicBounds(
                    drawables[0], drawables[1], mClearIconDrawable, drawables[3]
                )
            } else { // remove icon
                setCompoundDrawables(drawables[0], drawables[1], null, drawables[3])
            }
        }
        mIsClearIconShown = show
    }

    class ClearIconSavedState internal constructor(
        superState: Parcelable?,
        val isClearIconShown: Boolean
    ) : BaseSavedState(
        superState
    ) {

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeByte((if (isClearIconShown) 1 else 0).toByte())
        }

        override fun describeContents(): Int {
            return 0
        }
    }

    companion object {
        @DrawableRes
        private val DEFAULT_CLEAR_ICON_RES_ID: Int = R.drawable.ic_clear_text_input
    }

    init {
        init(context, attrs, defStyleAttr)
    }
}
