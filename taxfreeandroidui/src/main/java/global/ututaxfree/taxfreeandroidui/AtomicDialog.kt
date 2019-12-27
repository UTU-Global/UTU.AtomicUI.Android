package global.ututaxfree.taxfreeandroidui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import global.ututaxfree.taxfreeandroidui.databinding.DialogUiBinding
import global.ututaxfree.taxfreeandroidui.utilities.TaxFreeUtils

/**
 * Created by Bharath Simha Gupta on 2019-11-12.
 */
class AtomicDialog(
    private var title: String?, private var message: String,
    private var positiveTitle: String,
    private var negativeTitle: String,
    private var positiveListener: OnDialogButtonClickListener?,
    private var isDeleteUI: Boolean
) : DialogFragment() {

    lateinit var binding: DialogUiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setCanceledOnTouchOutside(false)
        binding = DialogUiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val transparent = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(transparent, TaxFreeUtils.pxToDp(16))
        dialog!!.window!!.setBackgroundDrawable(inset)

        if (!TextUtils.isEmpty(title)) {
            binding.title.text = title

        } else {
            binding.title.visibility = View.GONE
        }

        if (isDeleteUI) {
            binding.positive.setTextColor(
                Color.parseColor("#FFFFFF")
            )
            binding.negative.setTextColor(
                Color.parseColor("#e33d30")
            )
            binding.positive.background = ContextCompat.getDrawable(
                context!!,
                R.drawable.border_red_contained_dialog
            )
            binding.negative.background = ContextCompat.getDrawable(
                context!!,
                R.drawable.border_red_outlined_dialog
            )
        }
        binding.message.text = message
        binding.positive.text = positiveTitle
        binding.negative.text = negativeTitle

        binding.negative.setOnClickListener {
            dialog!!.dismiss()
            positiveListener?.onButtonClick(false)
        }

        binding.positive.setOnClickListener {
            dialog!!.dismiss()
            positiveListener?.onButtonClick(true)
        }
    }

    interface OnDialogButtonClickListener {
        fun onButtonClick(isPositive: Boolean)
    }
}