package global.ututaxfree.androidui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import global.ututaxfree.androidui.ContactsAdapter.ContactsViewHolder

/**
 * Created by Likhitha on 20/12/2019
 */
private class ContactsAdapter(private val contacts: List<Contact>, private val layoutId: Int) :
    RecyclerView.Adapter<ContactsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layoutId, null)
        return ContactsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        if (position == 0 || contacts[position - 1].index != contact.index) {
            holder.tvIndex.visibility = View.VISIBLE
            holder.tvIndex.text = contact.index
        } else {
            holder.tvIndex.visibility = View.GONE
        }
        holder.tvName.text = contact.name
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    internal inner class ContactsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvIndex: TextView
        var ivAvatar: ImageView
        var tvName: TextView

        init {
            tvIndex = itemView.findViewById<View>(R.id.tv_index) as TextView
            ivAvatar =
                itemView.findViewById<View>(R.id.iv_avatar) as ImageView
            tvName = itemView.findViewById<View>(R.id.tv_name) as TextView
            itemView.setOnClickListener { v ->
                Toast.makeText(v.context, tvName.text.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}