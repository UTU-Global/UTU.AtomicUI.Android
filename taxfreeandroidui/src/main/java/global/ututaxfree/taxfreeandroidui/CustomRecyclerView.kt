package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CustomRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val contacts = ArrayList<FriendsList>()
    init {
        initView()
        initData()
    }
    private fun initView() {
        val customView =  inflate(context, R.layout.activity_friendslist, this)
        val rvContacts = customView.findViewById<RecyclerView>(R.id.rv_contacts)
        rvContacts!!.layoutManager = LinearLayoutManager(context)
        rvContacts.adapter =
            FriendsListAdapter(
                contacts,
                R.layout.item_friendslist
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
        contacts.addAll(FriendsList.englishContacts)
    }

}