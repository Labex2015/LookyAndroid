package labex.feevale.br.looky.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.view.BaseFragment;

public class InteractionsFragment extends BaseFragment {

    private ListView interactionsListView;
    private BaseAdapter adapter;

    public InteractionsFragment() {}

    public InteractionsFragment(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interactions_fragment, container, false);
        if(view != null){
            interactionsListView = (ListView)view.findViewById(R.id.interactionsListView);
            TextView messageNoContent = (TextView) view.findViewById(R.id.noInteractionsMessage);
            interactionsListView.setEmptyView(messageNoContent);

            interactionsListView.setOnItemClickListener((AdapterView.OnItemClickListener) adapter);
            if(adapter instanceof AdapterView.OnItemLongClickListener)
                interactionsListView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener)adapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.interactionsListView.setAdapter(adapter);
    }


}
