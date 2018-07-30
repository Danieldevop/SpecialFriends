package us.happycart.specialfriends.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import us.happycart.specialfriends.R;

public class FacebookFriendsAdapterHorizontal extends RecyclerView.Adapter<FacebookFriendsAdapterHorizontal.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> fbImagesFriends, fbNamesFriends;
    private Context mContext;

    public FacebookFriendsAdapterHorizontal (Context mContext, ArrayList<String> fbImagesFriends, ArrayList<String> fbNamesFriends) {
        this.fbImagesFriends = fbImagesFriends;
        this.fbNamesFriends = fbNamesFriends;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FacebookFriendsAdapterHorizontal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_fb_friends_horizontal, parent, false);
        FacebookFriendsAdapterHorizontal.ViewHolder holder = new FacebookFriendsAdapterHorizontal.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FacebookFriendsAdapterHorizontal.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: LLAMADO.");

        Glide.with(mContext)
                .asBitmap()
                .load(fbImagesFriends.get(position))
                .into(holder.friendImage);

        holder.friendName.setText(fbNamesFriends.get(position));
    }

    @Override
    public int getItemCount() {
        return fbNamesFriends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView friendName;
        CircleImageView friendImage;

        public ViewHolder(View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.fb_profile_text_horizontal);
            friendImage = itemView.findViewById(R.id.fb_profile_image_horizontal);
        }

    }
}
