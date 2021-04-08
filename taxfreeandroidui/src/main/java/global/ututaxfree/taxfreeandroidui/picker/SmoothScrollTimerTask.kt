package global.ututaxfree.taxfreeandroidui.picker

import kotlin.math.abs

internal class SmoothScrollTimerTask(private val loopView: LoopView, private var offset: Int) : Runnable {
    var realTotalOffset: Int
    var realOffset: Int
    override fun run() {
        if (realTotalOffset == Int.MAX_VALUE) {
            realTotalOffset = offset
        }
        realOffset = (realTotalOffset.toFloat() * 0.1f).toInt()
        if (realOffset == 0) {
            realOffset = if (realTotalOffset < 0) {
                -1
            } else {
                1
            }
        }
        if (abs(realTotalOffset) <= 0) {
            loopView.cancelFuture()
            loopView.handler1!!.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED)
        } else {
            loopView.totalScrollY = loopView.totalScrollY + realOffset
            loopView.handler1!!.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW)
            realTotalOffset -= realOffset
        }
    }

    init {
        realTotalOffset = Int.MAX_VALUE
        realOffset = 0
    }
}