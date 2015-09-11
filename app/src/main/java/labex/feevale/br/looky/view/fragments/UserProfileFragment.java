package labex.feevale.br.looky.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Evaluation;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.model.UserProfile;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.adapters.KnowledgeListProfileAdapter;
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
        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
        knowledgesView = (ListView) view.findViewById(R.id.knowledgeProfileListView);

        View header = (View)inflater.inflate(R.layout.profile_list_header, null);
        usernameTextView = (TextView) header.findViewById(R.id.usernameProfileText);
        helpedTextView = (TextView)  header.findViewById(R.id.helpedProfileTextView);
        requestedTextView = (TextView)  header.findViewById(R.id.reqHelpProfileTextView);
        scoreTextView = (TextView)  header.findViewById(R.id.scoreProfileText);
        graduationTextView = (TextView)  header.findViewById(R.id.userGraduationProfileText);
        ratingBar = (RatingBar) header.findViewById(R.id.ratingScoreUserProfile);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new KnowledgeListProfileAdapter(profile.knowledges, getActivity());
        knowledgesView.setAdapter(adapter);
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

    private List<Evaluation> getEvaluations(){
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation(1L,3,4,"Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(1L,"bóris", null, 23.454545F, 32.343434F, "", "Ciência da Computação", 5, null, null)));
        evaluations.add(new Evaluation(2L,3,4,"Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(3L,"Carmem", null, 23.454545F, 32.343434F, "", "Contabilidade", 3, null, null)));
        evaluations.add(new Evaluation(4L,3,4,"Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(5L,"Jaques", null, 23.454545F, 32.343434F, "", "Moda", 5, null, null)));
        evaluations.add(new Evaluation(6L, 3, 4, "Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(7L, "Mariana", null, 23.454545F, 32.343434F, "", "Ciência da Computação", 8, null, null)));
        evaluations.add(new Evaluation(8L, 3, 4, "Me ajudou legal, mas mesmo assim fiquei com dúvida.",
                new Date(), new User(9L, "Tammer", null, 23.454545F, 32.343434F, "", "Engenharia Civil", 7, null, null)));
        return evaluations;
    }
}
