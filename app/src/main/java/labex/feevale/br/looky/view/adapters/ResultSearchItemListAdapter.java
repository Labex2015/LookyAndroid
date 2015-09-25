package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.UserProfile;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.view.custom.RoundedImageView;
import labex.feevale.br.looky.view.fragments.UserProfileFragment;

/**
 * Created by grimmjowjack on 8/17/15.
 */
public class ResultSearchItemListAdapter extends RecyclerView.Adapter<ResultSearchItemListAdapter.ViewHolder>{

    private Activity activity;
    private List<UserProfile> profiles;
    private View.OnClickListener listener;

    public ResultSearchItemListAdapter(Activity activity, View.OnClickListener listener) {
        this(new ArrayList<UserProfile>(), activity, listener);
    }

    public ResultSearchItemListAdapter(List<UserProfile> profiles, Activity activity, View.OnClickListener listener) {
        this.profiles = profiles;
        this.activity = activity;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView degree;
        public RatingBar rating;
        public RoundedImageView picture;

        public ViewHolder(View itemView, TextView username,
                          TextView degree, RatingBar rating, RoundedImageView picture,
                                                             View.OnClickListener listener) {
            super(itemView);
            this.username = username;
            this.degree = degree;
            this.rating = rating;
            this.picture = picture;

            itemView.setOnClickListener(listener);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.pre_profile_item_list, parent, false);
        TextView username = (TextView)view.findViewById(R.id.usernamePreTextView);
        TextView degree = (TextView)view.findViewById(R.id.degreePreTextView);
        RatingBar rating = (RatingBar) view.findViewById(R.id.ratingPreProfile);
        rating.setIsIndicator(true);
        RoundedImageView picture =  (RoundedImageView)view.findViewById(R.id.preUserPicture);

        return new ViewHolder(view,username,degree,rating,picture, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(profiles.get(position).getUsername());
        holder.degree.setText(profiles.get(position).getDegree());

        float ratingValue = ((profiles.get(position).answerPoints + profiles.get(position).helpPoints))
                * 5 / 2;
        holder.rating.setRating(ratingValue);
        Picasso.with(activity)
                .load(profiles.get(position).picturePath)
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .resize(150, 150)
                .centerCrop().into(holder.picture);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public List<UserProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<UserProfile> profiles) {
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    public void loadProfile(int position){
        loadProfileTask(profiles.get(position));
    }

    private void loadProfileTask(UserProfile item) {
        new LoadProfileCompleteTask(activity,item.getId()).makeRequest();
    }

    class LoadProfileCompleteTask extends RequestHandler<UserProfile>{

        protected LoadProfileCompleteTask(Context context, Long userId) {
            super(new UserProfile(), context, AppVariables.URL_USER_PROFILE
                    .replace(AppVariables.TAG_IDUSER, String.valueOf(userId)), RequestHandler.GET);
        }

        @Override
        protected void error(MessageResponse messageResponse) {
            Toast.makeText(activity, messageResponse.getMsg(), Toast.LENGTH_LONG).show();
        }

        @Override
        protected void close(UserProfile profile) {
            if(profile != null)
                ((MainActivity)activity).changeFragment(new UserProfileFragment(profile));
        }
    }

}
