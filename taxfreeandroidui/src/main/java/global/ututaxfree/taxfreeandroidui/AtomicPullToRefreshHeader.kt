package global.ututaxfree.taxfreeandroidui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.dinuscxj.refresh.IRefreshStatus

/**
 * Created by Bharath Grandhe on 27/05/21.
 */
@SuppressLint("ViewConstructor")
class AtomicPullToRefreshHeader(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val isHome: Boolean
) : FrameLayout(context, attrs, defStyleAttr), IRefreshStatus {

    init {
        onInit()
    }

    private fun onInit() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        val layout = if (!isHome) {
            inflater.inflate(R.layout.ui_refresh_wallet, null)
        } else {
            inflater.inflate(R.layout.ui_refresh_home, null)
        }
        addView(layout)
    }

    override fun reset() {
    }

    override fun refreshing() {
    }

    override fun refreshComplete() {

    }

    override fun pullToRefresh() {
    }

    override fun releaseToRefresh() {
    }

    override fun pullProgress(pullDistance: Float, pullProgress: Float) {

    }
}