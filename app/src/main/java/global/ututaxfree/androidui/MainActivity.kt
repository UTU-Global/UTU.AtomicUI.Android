package global.ututaxfree.androidui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import global.ututaxfree.taxfreeandroidui.AtomicToast
import global.ututaxfree.taxfreeandroidui.ToastClosedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taxFreeB.setOnClickListener {
            AtomicToast.show(
                context = applicationContext,
                view = mainLayout,
                actionText = "Testing Snackbar!!!!",
                actionType = AtomicToast.TYPE_ERROR,
                listener = object : ToastClosedListener {
                    override fun onToastClosed() {
                        Toast.makeText(applicationContext, "Toast closed", Toast.LENGTH_LONG).show()
                    }
                }
            )
        }
    }
}
