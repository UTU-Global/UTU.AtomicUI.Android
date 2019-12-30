package global.ututaxfree.taxfreeandroidui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Likhitha on 30/12/19.
 */
public class SelectedFriendAdapter extends RecyclerView.Adapter<SelectedFriendAdapter.ContactsViewHolder> {

    private ArrayList<String> selectedFriends;
    private int layoutId;
    Context context;
    AdapterListener listener;
    int selectedPosition;

    public SelectedFriendAdapter(Context context, ArrayList<String> selectedFriends, int layoutId, int selectedPosition
    ) {
        this.selectedFriends = selectedFriends;
        this.layoutId = layoutId;
        this.context = context;
        this.selectedPosition = selectedPosition;
        this.listener = listener;
        Log.d("fjgdddd", selectedFriends.toString());

    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        selectedFriends.get(position);
        for (int i = 0; i < selectedFriends.size(); i++) {
            holder.friendNameTv.setText(selectedFriends.get(i));
        }
        holder.closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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