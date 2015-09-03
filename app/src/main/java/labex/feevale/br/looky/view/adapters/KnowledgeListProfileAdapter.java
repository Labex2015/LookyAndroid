package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Knowledge;

/**
 * Created by grimmjowjack on 8/31/15.
 */
public class KnowledgeListProfileAdapter  extends BaseAdapter{

    private List<Knowledge> knowledges;
    private Activity activity;

    public KnowledgeListProfileAdapter(List<Knowledge> knowledges, Activity activity) {
        this.knowledges = knowledges;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return knowledges.size();
    }

    @Override
    public Object getItem(int i) {
        return knowledges.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.knowledge_item_list_profile, viewGroup, false);
        TextView name = (TextView) view.findViewById(R.id.knowledgeItemListProfile);
        name.setText(knowledges.get(i).getName());
        return view;
    }

    public List<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }
}
