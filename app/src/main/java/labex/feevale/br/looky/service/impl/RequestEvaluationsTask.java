package labex.feevale.br.looky.service.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Evaluation;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.utils.ExtraAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.mocks.ApplicationMocks;
import labex.feevale.br.looky.view.dialogs.EvaluationsProfileDialog;

/**
 * Created by 0126128 on 11/06/2015.
 */
public class RequestEvaluationsTask extends  AsyncTask<Void, Void,Void> implements ExtraAction<MessageResponse>{

    private Activity activity;
    private Long idUser;

    private List<Evaluation> evaluations;
    private ProgressDialog dialog;
    private MessageResponse messageResponse;

    public RequestEvaluationsTask(Activity activity, Long idUser) {
        this.activity = activity;
        this.idUser = idUser;
        evaluations = new ArrayList<Evaluation>();
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getText(R.string.load_evaluation_message_progress_dialog));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new RequestEvaluations(activity, idUser, evaluations, this).makeRequest();
        return null;
    }

    @Override
    protected void onPostExecute(final Void response) {
        if(dialog.isShowing())
            dialog.dismiss();
        if(this.messageResponse == null){
            EvaluationsProfileDialog dialog = new EvaluationsProfileDialog(evaluations, activity);
            dialog.createDialog();
        }else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, messageResponse.getMsg(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void executeAction(MessageResponse... params) {
        if(params != null && params.length == 1){
            this.messageResponse = params[0];
        }
    }

    class RequestEvaluations extends RequestHandler<List<Evaluation>>{
        ExtraAction<MessageResponse> extraAction;

        protected RequestEvaluations(Context context, Long idUser, List<Evaluation> evaluations, ExtraAction extraAction) {
            super(evaluations, context,
                    AppVariables.URL_INTERACTION.replace("#ID", idUser.toString()),
                    RequestHandler.GET);
            this.extraAction = extraAction;
        }

        @Override
        protected void error(MessageResponse response) {
            //extraAction.executeAction(response);
            evaluations.addAll(ApplicationMocks.getEvaluations(idUser));
        }

        @Override
        protected void close(List<Evaluation> evaluationsResponse) {
            evaluations.addAll(evaluationsResponse);
        }
    }
}
