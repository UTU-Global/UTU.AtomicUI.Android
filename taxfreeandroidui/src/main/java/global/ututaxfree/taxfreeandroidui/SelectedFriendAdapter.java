package global.ututaxfree.taxfreeandroidui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by gjz on 9/4/16.
 */
public class SelectedFriendAdapter extends RecyclerView.Adapter<SelectedFriendAdapter.ContactsViewHolder> {

    private List<String> selectedFriends;
    private int layoutId;

    public SelectedFriendAdapter(List<String> selectedFriends, int layoutId) {
        this.selectedFriends = selectedFriends;
        this.layoutId = layoutId;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return selectedFriends.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView friendNameTv;
        public ImageView closeImage;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            friendNameTv = (AppCompatTextView) itemView.findViewById(R.id.selected_friend_name);
            closeImage = (ImageView) itemView.findViewById(R.id.selected_friend_close_image);
        }
    }
}