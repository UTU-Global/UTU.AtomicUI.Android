package global.ututaxfree.taxfreeandroidui.pickerview

import android.os.Handler
import android.os.Message

/**
 * Created by Likhitha Kolla on 01/06/2020.
 */
class `AtomicPickerDOB$$Lambda$2` private constructor(private val `arg$1`: AtomicPickerDOB) :
    Handler.Callback {
    override fun handleMessage(var1: Message): Boolean {
        return false
    }

    companion object {
        private fun `get$Lambda`(var0: AtomicPickerDOB): Handler.Callback {
            return `AtomicPickerDOB$$Lambda$2`(var0)
        }

        fun `lambdaFactory$`(var0: AtomicPickerDOB): Handler.Callback {
            return `AtomicPickerDOB$$Lambda$2`(var0)
        }
    }

}