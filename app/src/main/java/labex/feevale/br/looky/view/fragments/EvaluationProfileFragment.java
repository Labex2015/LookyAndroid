package labex.feevale.br.looky.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Evaluation;
import labex.feevale.br.looky.view.adapters.EvaluationListProfileAdapter;

/**
 * Created by grimmjowjack on 8/31/15.
 */
public class EvaluationProfileFragment extends Fragment {

    private List<Evaluation> evaluations;

    private EvaluationListProfileAdapter adapter;
    private ListView listView;
    private int size;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        evaluations = getArguments().getParcelableArrayList("LISTA");
        size = getArguments().getInt("SIZE");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.evaluation_profile_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.evaluationProfileListView);
        view.setMinimumHeight(size);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new EvaluationListProfileAdapter(getActivity(), evaluations);
        listView.setAdapter(adapter);
    }
}



