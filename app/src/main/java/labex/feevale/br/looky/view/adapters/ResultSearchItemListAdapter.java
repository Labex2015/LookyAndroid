package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
public class ResultSearchItemListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Activity activity;
    private List<UserProfile> profiles;

    public ResultSearchItemListAdapter(Activity activity) {
        this(new ArrayList<UserProfile>(), activity);
    }

    public ResultSearchItemListAdapter(List<UserProfile> profiles, Activity activity) {
        this.profiles = profiles;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return profiles.size();
    }

    @Override
    public Object getItem(int i) {
        return profiles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.pre_profile_item_list, viewGroup, false);

        TextView username = (TextView)view.findViewById(R.id.usernamePreTextView);
        username.setText(profiles.get(position).getUsername());

        TextView degree = (TextView)view.findViewById(R.id.degreePreTextView);
        degree.setText(profiles.get(position).getDegree());

        float ratingValue = ((profiles.get(position).answerPoints + profiles.get(position).helpPoints))
                            * 5 / 2;
        RatingBar rating = (RatingBar) view.findViewById(R.id.ratingPreProfile);
        rating.setRating(ratingValue);
        rating.setIsIndicator(true);

        RoundedImageView picture =  (RoundedImageView)view.findViewById(R.id.preUserPicture);
        if(profiles.get(position).getPicture() != null)
            picture.setImageBitmap(BitmapFactory.decodeByteArray(profiles.get(position).getPicture(),
            picture.getWidth(), picture.getHeight()));

        return view;
    }

    public List<UserProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<UserProfile> profiles) {
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
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
