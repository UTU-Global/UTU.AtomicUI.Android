package global.ututaxfree.taxfreeandroidui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Likhitha on 30/12/19.
 */
public class SelectedFriendAdapter extends RecyclerView.Adapter<SelectedFriendAdapter.ContactsViewHolder> {

    private ArrayList<SelectedFriend> selectedFriends;
    private int layoutId;
    Context context;
    RemoveFriendListener listener;

    public SelectedFriendAdapter(Context context, ArrayList<SelectedFriend> selectedFriends,
                                 int layoutId, RemoveFriendListener listener
    ) {
        this.selectedFriends = selectedFriends;
        this.layoutId = layoutId;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {
        holder.friendNameTv.setText(selectedFriends.get(holder.getAdapterPosition()).getName());
        holder.closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFriendRemoved(selectedFriends.get
                        (holder.getAdapterPosition()).getPosition());
             /*   selectedFriends.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedFriends.size();
    }

    public void onUpdateSelectedFriendsList(ArrayList<SelectedFriend> arrayList) {
        this.selectedFriends = arrayList;
        notifyDataSetChanged();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView friendNameTv;
        public ImageView closeImage;
        public RelativeLayout selectedFriendLayout;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            friendNameTv = itemView.findViewById(R.id.selected_friend_name);
            closeImage = itemView.findViewById(R.id.selected_friend_close_image);
            selectedFriendLayout = itemView.findViewById(R.id.selectedFriendLayout);
        }
    }
}