package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.dinuscxj.refresh.IRefreshStatus
import kotlinx.android.synthetic.main.ui_refresh.view.*

/**
 * Created by Bharath Grandhe on 27/05/21.
 */
class AtomicSwipeToRefresh(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IRefreshStatus {

    init {
        onInit()
    }

    private fun onInit() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        val layout = inflater.inflate(R.layout.ui_refresh, null)

        addView(layout)
    }

    override fun reset() {
        avView.smoothToHide()
    }

    override fun refreshing() {
        avView.smoothToShow()
    }

    override fun refreshComplete() {

    }

    override fun pullToRefresh() {
        avView.smoothToShow()
    }

    override fun releaseToRefresh() {
        //avView.smoothToHide()
    }

    override fun pullProgress(pullDistance: Float, pullProgress: Float) {

    }
}