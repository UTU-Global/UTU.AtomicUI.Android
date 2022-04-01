package global.ututaxfree.taxfreeandroidui.picker

import android.os.Handler
import android.os.Message

internal class MessageHandler(private val loopview: LoopView) : Handler() {
    override fun handleMessage(msg: Message) {
        when (msg.what) {
            WHAT_INVALIDATE_LOOP_VIEW -> loopview.invalidate()
            WHAT_SMOOTH_SCROLL -> {
                removeMessages(WHAT_SMOOTH_SCROLL_INERTIA)
                loopview.smoothScroll(LoopView.ACTION.FLING)
            }
            WHAT_ITEM_SELECTED -> loopview.onItemSelected()
        }
    }

    companion object {
        const val WHAT_INVALIDATE_LOOP_VIEW = 1000
        const val WHAT_SMOOTH_SCROLL = 2000
        const val WHAT_SMOOTH_SCROLL_INERTIA = 2001
        const val WHAT_ITEM_SELECTED = 3000
    }
}