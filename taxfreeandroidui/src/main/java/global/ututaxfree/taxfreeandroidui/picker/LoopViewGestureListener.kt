package global.ututaxfree.taxfreeandroidui.picker

import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent

internal class LoopViewGestureListener(private val loopView: LoopView) : SimpleOnGestureListener() {
    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        loopView.scrollBy(velocityY)
        return true
    }
}