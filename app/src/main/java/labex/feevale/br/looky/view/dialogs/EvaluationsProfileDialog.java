package labex.feevale.br.looky.view.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Evaluation;
import labex.feevale.br.looky.view.dialogs.adapter.EvaluationListViewDialogAdapter;

/**
 * Created by 0126128 on 11/06/2015.
 */
public class EvaluationsProfileDialog{

    private Activity activity;
    private List<Evaluation> evaluations;

    private ImageButton closeButton;
    private ListView evaluationsListView;

    public EvaluationsProfileDialog(List<Evaluation> evaluations, Activity activity) {
        this.evaluations = evaluations;
        this.activity = activity;
    }

    public void createDialog() {
        Dialog dialog = new Dialog(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.evaluation_profile_dialog, null);
        evaluationsListView = (ListView) view.findViewById(R.id.evaluationDialogListView);
        evaluationsListView.setAdapter(new EvaluationListViewDialogAdapter(activity, evaluations));
        dialog.setTitle(activity.getString(R.string.evaluation_list_header));
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(600, 800);
        dialog.show();
    }


}
