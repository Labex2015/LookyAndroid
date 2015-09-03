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
import labex.feevale.br.looky.service.utils.ExtraAction;

/**
 * Created by 0126128 on 08/07/2015.
 */
public class KnowledgesUserAdapter extends BaseAdapter{

    private List<Knowledge> knowledges;
    private Activity activity;
    private ExtraAction<Knowledge> removeAction;

    public KnowledgesUserAdapter(List<Knowledge> knowledges, Activity activity, ExtraAction removeAction) {
        this.knowledges = knowledges;
        this.activity = activity;
        this.removeAction = removeAction;
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
        view = activity.getLayoutInflater().inflate(R.layout.knowledge_profile_item_list, viewGroup, false);

        ImageView removeImageView = (ImageView) view.findViewById(R.id.removeKnowledgeImageView);
        removeImageView.setOnClickListener(removeItemEventListener(getKnowledges().get(i)));

        TextView label = (TextView)view.findViewById(R.id.knowledgeAreaTextView);
        TextView labelDegree = (TextView)view.findViewById(R.id.knowledgeDegreeTextView);
        label.setText(knowledges.get(i).getName().toUpperCase());
        labelDegree.setText(knowledges.get(i).getSubjectName() != null ?
                            knowledges.get(i).getSubjectName() : "");

        return view;
    }

    private View.OnClickListener removeItemEventListener(final Knowledge toRemove) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAction.executeAction(toRemove);
            }
        };
    }

    public List<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }
}
