package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Evaluation;

/**
 * Created by grimmjowjack on 8/31/15.
 */
public class EvaluationListProfileAdapter extends BaseAdapter{

    private Activity activity;
    private List<Evaluation> evaluations;

    public EvaluationListProfileAdapter(Activity activity, List<Evaluation> evaluations) {
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
        view = activity.getLayoutInflater().inflate(R.layout.evaluation_item_list_profile, viewGroup, false);
        TextView name = (TextView) view.findViewById(R.id.evaluationNameItemListProfile);
        name.setText(evaluations.get(i).getUserEvaluator().getUsername());

        TextView degree = (TextView) view.findViewById(R.id.degreeProfileItemTextView);
        String degreeValue = evaluations.get(i).getUserEvaluator().getDegree();
        degree.setText(degreeValue != null ? degreeValue : "");

        RatingBar rating = (RatingBar) view.findViewById(R.id.profileRatingBar);
        rating.setRating((evaluations.get(i).getAnswerPoints()+evaluations.get(i).getHelpPoints())/2);

        return view;
    }
}
