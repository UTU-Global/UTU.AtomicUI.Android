package global.ututaxfree.androidui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import global.ututaxfree.taxfreeandroidui.AtomicToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // mainLayout.addView(AtomicBubble(applicationContext, "25"))
        headerWalletLayout.setWhiteHeader(true)
        val items = resources.getStringArray(
            R.array.darkMode
        ).toList()
        loop.setItems(
            items
        )

        val items1 = resources.getStringArray(
            R.array.darkMode
        ).toList()
        language.setItems(
            items1,windowManager
        )
    }
}
