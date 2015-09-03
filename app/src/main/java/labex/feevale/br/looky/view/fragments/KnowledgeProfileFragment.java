package labex.feevale.br.looky.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.view.adapters.KnowledgeListProfileAdapter;

/**
 * Created by grimmjowjack on 8/31/15.
 */
public class KnowledgeProfileFragment extends Fragment{

    private List<Knowledge> knowledges;

    private ListView listView;
    private KnowledgeListProfileAdapter adapter;
    private int size;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        knowledges = getArguments().getParcelableArrayList("LISTA");
        size = getArguments().getInt("SIZE");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knowledge_profile_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.knowledgeProfileListView);
        view.setMinimumHeight(size);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new KnowledgeListProfileAdapter(knowledges, getActivity());
        listView.setAdapter(adapter);
    }
}
