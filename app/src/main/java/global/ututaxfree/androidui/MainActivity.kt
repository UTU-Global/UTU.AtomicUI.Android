package global.ututaxfree.androidui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import global.ututaxfree.taxfreeandroidui.AtomicToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AtomicToast(
            context = applicationContext,
            view = headerWalletLayout,
            actionText = "testing purposeeeee",
            actionType = AtomicToast.TYPE_WARNING,
            listener = null,
            isIndefinite = true
        ).show()
    }


    override fun onResume() {
        super.onResume()
        window.statusBarColor =
            ContextCompat.getColor(applicationContext, android.R.color.transparent)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.setSoftInputMode(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

}
