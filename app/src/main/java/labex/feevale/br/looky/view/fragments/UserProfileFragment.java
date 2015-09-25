package labex.feevale.br.looky.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.UserProfile;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.adapters.KnowledgeListProfileAdapter;
import labex.feevale.br.looky.view.custom.RoundedImageView;

/**
 * Created by grimmjowjack on 8/10/15.
 */
public class UserProfileFragment extends BaseFragment {

    private RoundedImageView picture;
    private TextView usernameTextView, distanceTextView, helpedTextView, requestedTextView,
                     graduationTextView, scoreTextView;
    private RatingBar ratingBar;
    private ImageButton requestHelpButton, commentsButton;
    private ListView knowledgesView;
    private KnowledgeListProfileAdapter adapter;

    private UserProfile profile;

    public UserProfileFragment() {    }

    public UserProfileFragment(UserProfile profile) {
        this.profile = profile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
        knowledgesView = (ListView) view.findViewById(R.id.knowledgeProfileListView);

        View header = (View)inflater.inflate(R.layout.profile_list_header, null);
        usernameTextView = (TextView) header.findViewById(R.id.usernameProfileText);
        helpedTextView = (TextView)  header.findViewById(R.id.helpedProfileTextView);
        requestedTextView = (TextView)  header.findViewById(R.id.reqHelpProfileTextView);
        scoreTextView = (TextView)  header.findViewById(R.id.scoreProfileText);
        graduationTextView = (TextView)  header.findViewById(R.id.userGraduationProfileText);

        ratingBar = (RatingBar) header.findViewById(R.id.ratingScoreUserProfile);
        ratingBar.setIsIndicator(false);

        picture = (RoundedImageView) header.findViewById(R.id.userProfileImageView);

        commentsButton = (ImageButton) header.findViewById(R.id.commentsImageButton);
        commentsButton.setOnClickListener(loadCommentsFragment());

        knowledgesView.addHeaderView(header);
        loadLayoutData();
        return view;
    }

    private View.OnClickListener loadCommentsFragment() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(profile.getEvaluations() != null && profile.getEvaluations().size() > 0)
                        ((MainActivity)getActivity())
                                .changeFragment(new EvaluationProfileFragment(profile.getEvaluations()));
                    else
                        Toast.makeText(getActivity(),
                                "Este usuário ainda não possui nenhuma avaliação!", Toast.LENGTH_LONG).show();
                }
            };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chat_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new KnowledgeListProfileAdapter(profile.knowledges != null ? profile.knowledges
                                                  : new ArrayList<Knowledge>(), getActivity());
        knowledgesView.setAdapter(adapter);

        Picasso.with(getActivity())
                .load(profile.getPicturePath())
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .resize(175, 175)
                .centerCrop().into(picture);
    }

    private void loadLayoutData(){
        usernameTextView.setText(profile.getUsername());
        helpedTextView.setText(String.valueOf(profile.getHelper()));
        requestedTextView.setText(String.valueOf(profile.getRequester()));
        scoreTextView.setText(String.valueOf(profile.getAnswerPoints()
                + profile.getHelpPoints()));
        graduationTextView.setText(profile.getDegree());
        ratingBar.setRating(profile.getRating());
    }

}
