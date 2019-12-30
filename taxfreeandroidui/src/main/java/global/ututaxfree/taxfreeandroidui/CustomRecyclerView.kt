package global.ututaxfree.taxfreeandroidui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Likhitha on 24/12/2019
 */

class CustomRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val contacts = ArrayList<FriendsList>()
    private var selectedFriends = ArrayList<String>()
    lateinit var selectedFriendRecyclerview: RecyclerView

    init {
        initData()
        initView()
    }

    private fun initView() {
        val customView = inflate(context, R.layout.activity_friendslist, this)
        val rvContacts = customView.findViewById<RecyclerView>(R.id.rv_contacts)
        selectedFriendRecyclerview =
            customView.findViewById<RecyclerView>(R.id.selected_friend_recyclerview)

        rvContacts!!.layoutManager = LinearLayoutManager(context)
        rvContacts.adapter =
            FriendsListAdapter(
                context,
                contacts,
                R.layout.item_friendslist,
                object : AdapterListener {
                    override fun setAdapter( position: Int,storedFriendsNameList: List<String>) {
                        selectedFriends = storedFriendsNameList as ArrayList<String>
                        selectedFriendRecyclerview!!.layoutManager = GridLayoutManager(context, 3)
                        selectedFriendRecyclerview.adapter =
                            SelectedFriendAdapter(
                                context, selectedFriends,
                                R.layout.item_selectedfriend,position

                            )


                    }



                }

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
