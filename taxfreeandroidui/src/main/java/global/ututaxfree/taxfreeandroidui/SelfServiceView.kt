package global.ututaxfree.taxfreeandroidui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

/**
 * Created by Likhitha Kolla on 2020-06-23.
 */

@SuppressLint("ViewConstructor")
class SelfServiceView(
    context: Context?,
    attrs: AttributeSet?
) : FrameLayout(
    context!!, attrs
) {

    init {
        onInit()
    }

    private fun onInit() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        val layout = inflater.inflate(R.layout.item_self_service, null)
        addView(layout)
    }

}