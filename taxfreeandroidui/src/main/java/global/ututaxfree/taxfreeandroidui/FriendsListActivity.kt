//package global.ututaxfree.taxfreeandroidui
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import global.ututaxfree.taxfreeandroidui.FriendsList.Companion.englishContacts
//import global.ututaxfree.taxfreeandroidui.WaveSideBar.OnSelectIndexItemListener
//import java.util.*
//
//
///**
// * Created by Likhitha on 20/12/2019
// */
//class FriendsListActivity : AppCompatActivity() {
//    private var rvContacts: RecyclerView? = null
//    private var friendsRecyclerView : RecyclerView? = null
//    private var sideBar: WaveSideBar? = null
//    private val contacts = ArrayList<FriendsList>()
//    private val selectedFriends : String? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initData()
//        initView()
//    }
//
//    private fun initView() {
//        setContentView(R.layout.activity_friendslist)
//        rvContacts = findViewById<View>(R.id.rv_contacts) as RecyclerView
//        friendsRecyclerView = findViewById<View>(R.id.selected_friend_recyclerview) as RecyclerView
//        rvContacts!!.layoutManager = LinearLayoutManager(this)
//        rvContacts!!.adapter =
//            FriendsListAdapter(this,
//                contacts,
//                R.layout.item_friendslist
//            )
//
//        rvContacts!!.layoutManager = GridLayoutManager(this,3)
//        rvContacts!!.adapter =
//            SelectedFriendAdapter(
//                selectedFriends,
//                R.layout.item_selectedfriend
//            )
//
//        sideBar = findViewById<View>(R.id.side_bar) as WaveSideBar
//        sideBar!!.setOnSelectIndexItemListener(object : OnSelectIndexItemListener {
//            override fun onSelectIndexItem(index: String?) {
//                for (i in contacts.indices) {
//                    if (contacts[i].index == index) {
//                        (rvContacts!!.layoutManager as LinearLayoutManager?)!!.scrollToPositionWithOffset(
//                            i,
//                            0
//                        )
//                        return
//                    }
//                }
//            }
//        })
//    }
//
//    private fun initData() {
//        contacts.addAll(englishContacts)
//    }
//}