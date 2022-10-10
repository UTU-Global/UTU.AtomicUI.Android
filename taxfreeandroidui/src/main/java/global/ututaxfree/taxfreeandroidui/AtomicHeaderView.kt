package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.lokalise.sdk.LokaliseResources
import global.ututaxfree.taxfreeandroidui.utilities.TaxFreeUtils

/**
 * Created by Bharath Simha Gupta on 2019-10-27.
 */
class AtomicHeaderView(context: Context?, attrs: AttributeSet?) : LinearLayout(
    context, attrs
) {

    lateinit var navigationUpButton: ImageButton
    lateinit var menuButton: AppCompatTextView
    lateinit var headerLabel: AppCompatTextView
    private var isWhiteHeader = false
    lateinit var toolbar: Toolbar

    companion object {
        const val BACK = "back"
        const val CLOSE = "close"

        const val WHITE = 1
        const val BLACK = 0
    }

    init {
        if (!isInEditMode) {
            onInit(attrs)
        }
    }

    private fun onInit(attrs: AttributeSet?) {
        if (!isInEditMode) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
            val header = inflater.inflate(R.layout.header_view, null)

            navigationUpButton = header.findViewById(R.id.headerAction)
            menuButton = header.findViewById(R.id.headerMenu)
            headerLabel = header.findViewById(R.id.headerText)
            toolbar = header.findViewById(R.id.my_toolbar)

            val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.AtomicHeaderView, 0, 0
            )

            val headerLabel =
                a.getString(R.styleable.AtomicHeaderView_headerLabel)
            if (!isInEditMode) {
                val resources = LokaliseResources(context)
                val newKey: String? = headerLabel?.let { resources.getString(it) }
                if (newKey != null) {
                    this.headerLabel.text = newKey
                }
            }
            val background =
                a.getColor(
                    R.styleable.AtomicHeaderView_toolbarBackgroundColor,
                    ContextCompat.getColor(context, R.color.colorWhite1)
                )
            toolbar.setBackgroundColor(background)
            isWhiteHeader = a.getBoolean(R.styleable.AtomicHeaderView_isWhiteHeader, false)
            onSetBackground()


            val isTransparent = a.getBoolean(
                R.styleable.AtomicHeaderView_isTransparentHeader, false
            )
            if (isTransparent) {
                toolbar.background =
                    ColorDrawable(Color.TRANSPARENT)
            }


            val menuText = a.getString(R.styleable.AtomicHeaderView_menuText)
            val menuIcon = a.getResourceId(R.styleable.AtomicHeaderView_menuIcon, -1)

            if (!TextUtils.isEmpty(menuText)) {
                menuButton.text = menuText
            }

            if (menuIcon != -1) {
                header.findViewById<AppCompatTextView>(R.id.headerMenu)
                    .setCompoundDrawablesWithIntrinsicBounds(
                        menuIcon, 0, 0, 0
                    )
            }

            if (TextUtils.isEmpty(menuText) && menuIcon == -1) {
                val params = menuButton.layoutParams
                params.height = TaxFreeUtils.pxToDp(24)
                params.width = TaxFreeUtils.pxToDp(24)
                menuButton.layoutParams = params
            }

            when (a.getString(R.styleable.AtomicHeaderView_actionType)) {

                BACK -> {
                    when (a.getInt(R.styleable.AtomicHeaderView_actionIconColor, BLACK)) {
                        WHITE -> {
                            header.findViewById<AppCompatTextView>(R.id.headerText)
                                .setTextColor(
                                    ContextCompat.getColor(
                                        context!!, R.color.whiteHeaderTextColor
                                    )
                                )
                            navigationUpButton.setImageResource(R.drawable.ic_back_white)
                        }
                        BLACK -> {
                            header.findViewById<AppCompatTextView>(R.id.headerText)
                                .setTextColor(
                                    ContextCompat.getColor(context!!, R.color.blackHeaderTextColor)
                                )
                            navigationUpButton.setImageResource(R.drawable.ic_back_black)
                        }
                    }
                }
                CLOSE -> {
                    when (a.getInt(R.styleable.AtomicHeaderView_actionIconColor, BLACK)) {
                        WHITE -> {
                            header.findViewById<AppCompatTextView>(R.id.headerText)
                                .setTextColor(
                                    ContextCompat.getColor(
                                        context!!,
                                        R.color.whiteHeaderTextColor
                                    )
                                )
                            if (isTransparent) {
                                navigationUpButton.setImageResource(R.drawable.ic_close_scan)
                            } else {
                                navigationUpButton.setImageResource(R.drawable.ic_close_white)
                            }
                        }
                        BLACK -> {
                            header.findViewById<AppCompatTextView>(R.id.headerText)
                                .setTextColor(
                                    ContextCompat.getColor(
                                        context!!,
                                        R.color.blackHeaderTextColor
                                    )
                                )
                            navigationUpButton.setImageResource(R.drawable.ic_close_black)
                        }
                    }
                }

                else -> {
                    val params = navigationUpButton.layoutParams
                    params.height = TaxFreeUtils.pxToDp(24)
                    params.width = TaxFreeUtils.pxToDp(24)
                    navigationUpButton.layoutParams = params
                }

            }
            orientation = VERTICAL
            gravity = Gravity.CENTER_VERTICAL

            addView(header)

            a.recycle()
        }
    }

    private fun onSetBackground() {
        if (isWhiteHeader) {
            toolbar.setBackgroundColor(
                ContextCompat.getColor(
                    context!!, R.color.colorWhite
                )
            )
            navigationUpButton.setImageResource(R.drawable.ic_back_black_arrow)
            headerLabel.setTextColor(
                ContextCompat.getColor(
                    context!!, R.color.colorBlack
                )
            )

        }
    }

    fun setWhiteHeader(isWhite: Boolean) {
        isWhiteHeader = isWhite
        onSetBackground()
    }
}