package global.ututaxfree.taxfreeandroidui.picker

internal class OnItemSelectedRunnable(private val loopView: LoopView) : Runnable {
    override fun run() {
        loopView.onItemSelectedListener!!.onItemSelected(loopView.selectedItem)
    }
}