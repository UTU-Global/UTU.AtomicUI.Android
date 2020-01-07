package global.ututaxfree.androidui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import global.ututaxfree.taxfreeandroidui.AtomicDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        taxFreeB.setOnClickListener {
//            /*  AtomicToast.show(
//                  context = applicationContext,
//                  view = mainLayout,
//                  actionText = "Testing Snackbar!!!!",
//                  actionType = AtomicToast.TYPE_ERROR,
//                  listener = object : ToastClosedListener {
//                      override fun onToastClosed() {
//                          Toast.makeText(applicationContext, "Toast closed", Toast.LENGTH_LONG).show()
//                      }
//                  }
//              )*/
//
//            AtomicDialog(
//                "Log out", "Are you sure you want to log out?",
//                "Log out", "Cancel",
//                object : AtomicDialog.OnDialogButtonClickListener {
//                    override fun onButtonClick(isPositive: Boolean) {
//
//                    }
//
//                }, false
//            ).show(supportFragmentManager, "LogoutDialog")
//        }
    }
}
