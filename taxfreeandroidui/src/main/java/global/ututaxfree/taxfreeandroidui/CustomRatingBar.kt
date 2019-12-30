package com.example.customratingbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView
import android.widget.LinearLayout
import global.ututaxfree.taxfreeandroidui.R

/**
 * Created by Likhitha on 24/12/2019
 */

class CustomRatingBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var mMaxRating = 0

    private var mFilledDrawable: Drawable? = null

    private var mUnfilledDrawable: Drawable? = null

    var rating = 0
        private set

    override fun onClick(v: View) {
        rating = v.tag as Int
        drawRatingViews()
        val eventType = if (Build.VERSION.SDK_INT >= 23) AccessibilityEvent.TYPE_ANNOUNCEMENT else AccessibilityEvent.TYPE_VIEW_FOCUSED
        sendAccessibilityEvent(eventType)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        drawRatingViews()
    }


    private fun drawRatingViews() {
        if (this.childCount == 0) {
            createRatingViews()
        } else {
            updateRatingViews()
        }
    }


    private fun createRatingViews() {
        for (i in 0 until mMaxRating) {
            val imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT)
            val tagValue = i + 1
            imageView.tag = tagValue
            imageView.contentDescription = context.getString(
                R.string.feedback_rating_value, tagValue)
            imageView.setImageDrawable(mUnfilledDrawable)
            imageView.setOnClickListener(this)
            addView(imageView)
        }
    }


    private fun updateRatingViews() {
        for (i in 0 until mMaxRating) {
            val view = getChildAt(i) as ImageView
            view.setImageDrawable(if (i + 1 <= rating) mFilledDrawable else mUnfilledDrawable)
        }
    }

    companion object {

        private const val DEFAULT_MAX_RATING = 5

        private  val DEFAULT_UNFILLED_DRAWABLE_ID = R.drawable.ic_star_empty

        private  val DEFAULT_FILLED_DRAWABLE_ID = R.drawable.ic_star_filled
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomRatingBar,
            0, 0)
        try {
            mMaxRating = typedArray.getInt(R.styleable.CustomRatingBar_maxRating,
                DEFAULT_MAX_RATING)
            mFilledDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_filledDrawable)
            if (mFilledDrawable == null) {
                mFilledDrawable = ResourcesCompat.getDrawable(resources,
                    DEFAULT_FILLED_DRAWABLE_ID, null)
            }
            mUnfilledDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_unfilledDrawable)
            if (mUnfilledDrawable == null) {
                mUnfilledDrawable = ResourcesCompat.getDrawable(resources,
                    DEFAULT_UNFILLED_DRAWABLE_ID, null)
            }
        } finally {
            typedArray.recycle()
        }
        isSaveEnabled = true
    }
}