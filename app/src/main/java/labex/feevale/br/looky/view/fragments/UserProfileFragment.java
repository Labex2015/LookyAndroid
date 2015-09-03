package labex.feevale.br.looky.view.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Evaluation;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.model.UserProfile;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.custom.RoundedImageView;

/**
 * Created by grimmjowjack on 8/10/15.
 */
public class UserProfileFragment extends BaseFragment {

    public static final String TAB_KNOWLEDGES = "Conhecimentos", TAB_EVALUATIONS = "Avaliações";

    private RoundedImageView picture;
    private TextView usernameTextView, distanceTextView, helpedTextView, requestedTextView,
                     graduationTextView, scoreTextView;
    private RatingBar ratingBar;
    private ImageButton requestHelpButton;

    private UserProfile profile;

    public UserProfileFragment() {    }

    public UserProfileFragment(UserProfile profile) {
        this.profile = profile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);

        usernameTextView = (TextView) view.findViewById(R.id.usernameProfileText);
        helpedTextView = (TextView)  view.findViewById(R.id.helpedProfileTextView);
        requestedTextView = (TextView)  view.findViewById(R.id.reqHelpProfileTextView);
        scoreTextView = (TextView)  view.findViewById(R.id.scoreProfileText);
        graduationTextView = (TextView)  view.findViewById(R.id.userGraduationProfileText);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingScoreUserProfile);


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point sizeP = new Point();
        display.getSize(sizeP);

        int size = Double.valueOf(sizeP.y * 0.4).intValue();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LISTA", (ArrayList<Knowledge>) profile.getKnowledges());
        bundle.putInt("SIZE", size);


        Bundle bundleComments = new Bundle();
        bundleComments.putInt("SIZE", size);
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation(1L,3,4,"Me ajudou legal, mas mesmo assim fiquei com dúvida.",
            new Date(), new User(1L,"bóris", null, 23.454545F, 32.343434F, "", "Ciência da Computação", 5, null, null)));
        evaluations.add(new Evaluation(2L,3,4,"Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(3L,"Carmem", null, 23.454545F, 32.343434F, "", "Contabilidade", 3, null, null)));
        evaluations.add(new Evaluation(4L,3,4,"Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(5L,"Jaques", null, 23.454545F, 32.343434F, "", "Moda", 5, null, null)));
        evaluations.add(new Evaluation(6L,3,4,"Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(7L,"Mariana", null, 23.454545F, 32.343434F, "", "Ciência da Computação", 8, null, null)));
        evaluations.add(new Evaluation(8L,3,4,"Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(9L,"Tammer", null, 23.454545F, 32.343434F, "", "Engenharia Civil", 7, null, null)));
        bundleComments.putParcelableArrayList("LISTA", (ArrayList<Evaluation>) evaluations);
        loadLayoutData();
        return view;
    }


    private void loadLayoutData(){
        usernameTextView.setText(profile.getUsername());
        helpedTextView.setText(String.valueOf(profile.getHelper()));
        requestedTextView.setText(String.valueOf(profile.getRequester()));
        scoreTextView.setText(String.valueOf(profile.getAnswerPoints()
                +profile.getHelpPoints()));
        graduationTextView.setText(profile.getDegree());
        ratingBar.setRating(profile.getRating());
    }
}
