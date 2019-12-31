package global.ututaxfree.taxfreeandroidui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by likhitha on 9/4/16.
 */
public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ContactsViewHolder> {

    private List<FriendsList> contacts;
    private List<SelectedFriend> storedFriendsNameList;
    private int layoutId;
    Context context;
    private AdapterListener listener;

    public FriendsListAdapter(Context context, List<FriendsList> contacts, int layoutId, AdapterListener listener) {
        this.contacts = contacts;
        this.context = context;
        this.layoutId = layoutId;
        this.listener = listener;
        storedFriendsNameList = new ArrayList<>();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {
        final FriendsList contact = contacts.get(holder.getAdapterPosition());
        if (position == 0 || !contacts.get(position - 1).getIndex().equals(contact.getIndex())) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contact.getIndex());
        } else {
            holder.tvIndex.setVisibility(View.GONE);
        }

        holder.tvName.setText(contact.getName());

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.radioButton.isSelected()) {
                    holder.radioButton.setChecked(true);
                    holder.radioButton.setSelected(true);
                    storedFriendsNameList.add(new SelectedFriend(contact.getName(),
                            holder.getAdapterPosition()));
                    listener.onAdapterDataChanged(holder.getAdapterPosition(), storedFriendsNameList);
                } else {
                    holder.radioButton.setChecked(false);
                    holder.radioButton.setSelected(false);
                    storedFriendsNameList.remove(contact);
                    listener.onAdapterDataChanged(holder.getAdapterPosition(), storedFriendsNameList);
                    notifyDataSetChanged();
                }
            }
        });
    }

    public void onRemoveSelectedFriendsList(int selectedPos) {
        storedFriendsNameList.remove(selectedPos);

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIndex;
        public ImageView ivAvatar;
        public TextView tvName;
        public RadioButton radioButton;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            radioButton = (RadioButton) itemView.findViewById(R.id.radioButton);
        }
    }
}