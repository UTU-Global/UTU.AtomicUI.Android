package global.ututaxfree.androidui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import global.ututaxfree.androidui.Contact.Companion.englishContacts
import global.ututaxfree.taxfreeandroidui.WaveSideBar
import global.ututaxfree.taxfreeandroidui.WaveSideBar.OnSelectIndexItemListener
import java.util.*

/**
 * Created by Likhitha on 20/12/2019
 */
class ContactsActivity : AppCompatActivity() {
    private var rvContacts: RecyclerView? = null
    private var sideBar: WaveSideBar? = null
    private val contacts = ArrayList<Contact>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initView()
    }

    private fun initView() {
        setContentView(R.layout.activity_contacts)
        rvContacts = findViewById<View>(R.id.rv_contacts) as RecyclerView
        rvContacts!!.layoutManager = LinearLayoutManager(this)
        rvContacts!!.adapter = ContactsAdapter(contacts, R.layout.item_contacts)
        sideBar = findViewById<View>(R.id.side_bar) as WaveSideBar
        sideBar!!.setOnSelectIndexItemListener(object : OnSelectIndexItemListener {
            override fun onSelectIndexItem(index: String?) {
                for (i in contacts.indices) {
                    if (contacts[i].index == index) {
                        (rvContacts!!.layoutManager as LinearLayoutManager?)!!.scrollToPositionWithOffset(
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
        contacts.addAll(englishContacts)
    }
}