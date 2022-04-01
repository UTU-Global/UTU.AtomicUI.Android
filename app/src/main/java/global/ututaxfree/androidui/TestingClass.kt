package global.ututaxfree.androidui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import global.ututaxfree.taxfreeandroidui.ToastClosedListener

/**
 * Created by Bharath Simha Gupta on 10/25/2019.
 */

class TestingClass : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listener = object : ToastClosedListener {
            override fun onToastClosed() {

            }
        }
    }
}
