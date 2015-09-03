package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.service.mod.EvaluationMod;

/**
 * Created by 0126128 on 09/06/2015.
 */
public class EvaluationProfileListViewAdapter extends BaseAdapter {

    private List<EvaluationMod> modList;
    private Activity activity;

    public EvaluationProfileListViewAdapter() {}

    public EvaluationProfileListViewAdapter(List<EvaluationMod> modList, Activity activity) {
        this.modList = modList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return modList.size();
    }

    @Override
    public Object getItem(int i) {
        return modList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.evaluation_profile_item_list, null);
        TextView name = (TextView) view.findViewById(R.id.nameCommentTextView);
        TextView comment = (TextView) view.findViewById(R.id.commentTextView);

        name.setText(modList.get(i).avaliator);
        comment.setText(modList.get(i).comment);
        return view;
    }
}
