package labex.feevale.br.looky.view.dialogs.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Evaluation;
import labex.feevale.br.looky.utils.DateUtils;

/**
 * Created by 0126128 on 11/06/2015.
 */
public class EvaluationListViewDialogAdapter extends BaseAdapter{

    private Activity activity;
    private List<Evaluation> evaluations;

    public EvaluationListViewDialogAdapter(Activity activity, List<Evaluation> evaluations) {
        this.activity = activity;
        this.evaluations = evaluations;
    }

    @Override
    public int getCount() {
        return evaluations.size();
    }

    @Override
    public Object getItem(int i) {
        return evaluations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.eval_list_item_dialog, null);

        TextView comment = (TextView) view.findViewById(R.id.commentDlgTextView);
        TextView name = (TextView) view.findViewById(R.id.evaluatorNameTextView);
        TextView date = (TextView) view.findViewById(R.id.dateAvaliationDlgTextView);

        comment.setText(evaluations.get(i).getComment());
        name.setText(evaluations.get(i).getUserEvaluator().getUsername());
        date.setText(DateUtils.dateToStringLong(evaluations.get(i).getEvaluated()));

        return view;
    }
}
