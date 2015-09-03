package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;

/**
 * Created by 0126128 on 09/06/2015.
 */
public class KnowledgeProfileListViewAdapter extends BaseAdapter {

    private List<Knowledge> knowledgeList;
    private Activity activity;

    public KnowledgeProfileListViewAdapter(List<Knowledge> knowledgeList, Activity activity) {
        this.knowledgeList = knowledgeList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return knowledgeList.size();
    }

    @Override
    public Object getItem(int i) {
        return knowledgeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.knowledge_profile_item_list, null);
        TextView knowledge = (TextView) view.findViewById(R.id.knowledgeAreaTextView);
        //knowledge.setText(knowledges.get(keys.get(i)));
        knowledge.setText(knowledgeList.get(i).getName());
        ImageView levelImage = (ImageView) view.findViewById(R.id.removeKnowledgeImageView);
        return view;
    }
}
