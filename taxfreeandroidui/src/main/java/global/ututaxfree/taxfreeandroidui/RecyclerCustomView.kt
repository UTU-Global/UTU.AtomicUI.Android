package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class RecyclerCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val contacts = ArrayList<Contact>()
    init {
        initView()
        initData()
    }
    private fun initView() {
        val customView =    inflate(getContext(), R.layout.activity_contacts, this);
        val rvContacts = customView.findViewById<RecyclerView>(R.id.rv_contacts)
        rvContacts!!.layoutManager = LinearLayoutManager(context)
        rvContacts.adapter =
            ContactsAdapter(
                contacts,
                R.layout.item_contacts
            )
        val sideBar = customView.findViewById<WaveSideBar>(R.id.side_bar)
        sideBar!!.setOnSelectIndexItemListener(object : WaveSideBar.OnSelectIndexItemListener {
            override fun onSelectIndexItem(index: String?) {
                for (i in contacts.indices) {
                    if (contacts[i].index == index) {
                        (rvContacts.layoutManager as LinearLayoutManager?)!!.scrollToPositionWithOffset(
                            i,
                            0
                        )
                        return
                    }
                }
            }
        })
    }


    private fun initData() {
        contacts.addAll(Contact.englishContacts)
    }

}