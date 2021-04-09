package global.ututaxfree.taxfreeandroidui.picker

import android.util.Log
import kotlin.math.abs

internal class InertiaTimerTask(private val loopView: LoopView, private val velocityY: Float) : Runnable {
    private var a: Float
    override fun run() {
        if (a == Int.MAX_VALUE.toFloat()) {
            a = if (abs(velocityY) > 2000f) {
                if (velocityY > 0.0f) {
                    2000f
                } else {
                    -2000f
                }
            } else {
                velocityY
            }
        }
        if (abs(a) in 0.0f..20f) {
            Log.i("gy", "WHAT_SMOOTH_SCROLL_INERTIA")
            loopView.handler1!!.sendEmptyMessageDelayed(MessageHandler.WHAT_SMOOTH_SCROLL_INERTIA, 60)
            loopView.cancelFuture()
            loopView.handler1!!.sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL)
            return
        }
        val i = (a * 10f / 1000f).toInt()
        val loopview = loopView
        loopview.totalScrollY = loopview.totalScrollY - i
        if (!loopView.isLoop) {
            val itemHeight = loopView.lineSpacingMultiplier * loopView.itemTextHeight
            if (loopView.totalScrollY <= ((-loopView.initPosition).toFloat() * itemHeight).toInt()) {
                a = 40f
                loopView.totalScrollY = ((-loopView.initPosition).toFloat() * itemHeight).toInt()
            } else if (loopView.totalScrollY >= ((loopView.items!!.size - 1 - loopView.initPosition).toFloat() * itemHeight).toInt()) {
                loopView.totalScrollY =
                    ((loopView.items!!.size - 1 - loopView.initPosition).toFloat() * itemHeight).toInt()
                a = -40f
            }
        }
        a = if (a < 0.0f) {
            a + 20f
        } else {
            a - 20f
        }
        loopView.handler1!!.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW)
    }

    init {
        a = Int.MAX_VALUE.toFloat()
    }
}