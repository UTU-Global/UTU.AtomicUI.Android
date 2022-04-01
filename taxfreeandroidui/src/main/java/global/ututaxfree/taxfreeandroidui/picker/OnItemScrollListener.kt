package global.ututaxfree.taxfreeandroidui.picker

interface OnItemScrollListener {
    fun onItemScrollStateChanged(
        loopView: LoopView?,
        currentPassItem: Int,
        oldScrollState: Int,
        scrollState: Int,
        totalScrollY: Int
    )

    fun onItemScrolling(
        loopView: LoopView?,
        currentPassItem: Int,
        scrollState: Int,
        totalScrollY: Int
    )
}