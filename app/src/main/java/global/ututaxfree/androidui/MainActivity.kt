package global.ututaxfree.androidui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import global.ututaxfree.taxfreeandroidui.AtomicToast
import global.ututaxfree.taxfreeandroidui.picker.LoopView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val darkModeView =
            findViewById(R.id.loop) as LoopView
        val items = resources.getStringArray(
            R.array.lang
        ).toList()
        darkModeView.setItems(
            items
        )
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
