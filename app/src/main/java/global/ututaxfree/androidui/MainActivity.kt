package global.ututaxfree.androidui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import global.ututaxfree.taxfreeandroidui.AtomicToast
import global.ututaxfree.taxfreeandroidui.ToastClosedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var toast: AtomicToast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taxFreeB.setOnClickListener {
            toast = AtomicToast(
                context = applicationContext,
                view = mainLayout,
                actionText = "Testing Snackbar!!!! asdyfgoas a uasdifuapsudhf oash opfiasdpo iasoi foaisdj oasij askdgf alshfasbdf asd",
                actionType = AtomicToast.TYPE_ERROR,
                listener = object : ToastClosedListener {
                    override fun onToastClosed() {
                    }
                })
            toast!!.show()
        }
    }

    override fun onBackPressed() {
        if (toast != null) {
            toast!!.dismiss()
        }
    }
}
