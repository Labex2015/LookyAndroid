package labex.feevale.br.looky.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.service.impl.RequestEvaluationsTask;
import labex.feevale.br.looky.service.impl.RequestHelpTask;
import labex.feevale.br.looky.service.mod.ProfileMod;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.adapters.EvaluationProfileListViewAdapter;
import labex.feevale.br.looky.view.adapters.KnowledgeProfileListViewAdapter;
import labex.feevale.br.looky.view.dialogs.DialogActions;
import labex.feevale.br.looky.view.dialogs.DialogMaker;

/**
 * Created by 0126128 on 08/06/2015.
 */
public class ProfileFragment extends BaseFragment implements DialogActions{

    private ProfileMod profile;
    private ListView knowledgeListView;
    private ListView evaluationListView;

    private View actionBar;
    private ImageButton backButton;
    private ImageButton requestHelpButton;

    private ImageButton evaluationButton;

    public ProfileFragment() {}

    public ProfileFragment(ProfileMod profile) {
        this.profile = profile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView nameTextView = (TextView)view.findViewById(R.id.profileUsernameTexteView);
        TextView courseTextView = (TextView)view.findViewById(R.id.courseTextView);
        TextView semesterTextView = (TextView)view.findViewById(R.id.semesterTextView);

        actionBar = view.findViewById(R.id.profileActionBar);
        backButton = (ImageButton)actionBar.findViewById(R.id.backProfileButton);
        requestHelpButton = (ImageButton)actionBar.findViewById(R.id.requestHelpButton);


        knowledgeListView = (ListView) view.findViewById(R.id.knowledgeListView);
        evaluationListView = (ListView) view.findViewById(R.id.avaliationListView);


        View knowledgeHeader = inflater.inflate(R.layout.knowledge_profile_header, null);
        knowledgeListView.addHeaderView(knowledgeHeader);

        View evaluationHeader = inflater.inflate(R.layout.evaluation_profile_header, null);
        evaluationListView.addHeaderView(evaluationHeader);
        evaluationButton = (ImageButton)evaluationHeader.findViewById(R.id.evaluationsHeaderButton);

        nameTextView.setText(profile.userProfile.getName());
        courseTextView.setText(profile.userProfile.getDegree());
        semesterTextView.setText(String.format("%s Semestre", profile.userProfile.getSemester()));

        assemblyEventListeners();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        evaluationListView.setAdapter(new EvaluationProfileListViewAdapter(profile.evaluationsList, getActivity()));
        knowledgeListView.setAdapter(new KnowledgeProfileListViewAdapter(profile.knowledgeList, getActivity()));
    }



    @Override
    public void cancelAction() {}

    @Override
    public void confirmAction() {
        new RequestHelpTask(profile.userProfile, getActivity()).execute();
    }

    public void assemblyEventListeners(){
        backButton.setOnClickListener(goBackEventListener());
        requestHelpButton.setOnClickListener(requestHelpEventListener());
        evaluationButton.setOnClickListener(listAllEvaluationEventListener());
    }


    private View.OnClickListener listAllEvaluationEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RequestEvaluationsTask(getActivity(), profile.userProfile.getId()).execute();
            }
        };
    }

    private View.OnClickListener requestHelpEventListener() {
        final DialogActions dialogActions = this;
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogMaker("Solicitacao de ajuda!",
                        String.format("Deseja solicitar a ajuda de %s ?",
                                profile.userProfile.getUsername()),dialogActions)
                        .createDialog(getActivity()).show();
            }
        };
    }

    private View.OnClickListener goBackEventListener() {
        final Activity activity = getActivity();
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        };
    }

}
