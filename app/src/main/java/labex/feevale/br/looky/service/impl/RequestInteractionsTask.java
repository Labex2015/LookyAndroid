package labex.feevale.br.looky.service.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.model.Interaction;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.utils.ExtraAction;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by 0126128 on 15/06/2015.
 */
public class RequestInteractionsTask extends AsyncTask<Void, Void, Void> implements ExtraAction<MessageResponse>{

    private Activity activity;
    private List<Interaction> interactions;
    private ProgressDialog dialog;
    private MessageResponse response;
    private User me;

    private TaskExtraAction taskExtraAction;
    private String url;

    public RequestInteractionsTask(Activity activity, String url, TaskExtraAction taskExtraAction) {
        this.activity = activity;
        me = new SharedPreferencesUtils().getUSer(activity);
        this.taskExtraAction = taskExtraAction;
        this.url = url;

    }

    @Override
    protected void onPreExecute() {
        ((MainActivity)activity).manageProgressView(false, "Listando interações.....");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        interactions = new ArrayList<Interaction>();
        new RequestMyInteractions(interactions, activity,this, this.url).makeRequest();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ((MainActivity)activity).manageProgressView(true);

        if(response == null) {
            taskExtraAction.onPostExecuteSuccess(interactions.toArray(new Interaction[interactions.size()]));
        }else
            Toast.makeText(activity, response.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void executeAction(MessageResponse... params) {
        if(params != null && params.length > 0)
            this.response = params[0];
    }


    class RequestMyInteractions extends RequestHandler<List<Interaction>>{

        private ExtraAction<MessageResponse> extraAction;

        public RequestMyInteractions(List<Interaction> interactions, Context context, ExtraAction extraAction, String url) {
            super(interactions, context, url, RequestHandler.GET);
            this.extraAction = extraAction;
        }

        @Override
        protected void error(MessageResponse messageResponse) {
            extraAction.executeAction(messageResponse);
        }

        @Override
        protected void close(List<Interaction> interactionsResponse) {
            interactions.addAll(interactionsResponse);
        }

        @Override
        protected List<Interaction> postExecuteCore(String response) {
            return new JsonUtils().JsonToListInteractions(response);
        }
    }
}
