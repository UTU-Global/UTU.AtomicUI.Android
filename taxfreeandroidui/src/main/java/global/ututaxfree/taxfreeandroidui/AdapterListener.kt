package global.ututaxfree.taxfreeandroidui

interface AdapterListener {
    fun onAdapterDataChanged(position : Int,storedFriendsNameList: List<SelectedFriend>)
}