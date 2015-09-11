package labex.feevale.br.looky.service.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.RequestHelp;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 10/06/2015.
 */
public class RequestHelpTask extends AsyncTask<Void, Void,MessageResponse> {

    private User user;
    private Activity activity;
    private ProgressDialog dialog;

    public RequestHelpTask(User user, Activity activity) {

        this.user = user;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getText(R.string.request_help_dialog));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected MessageResponse doInBackground(Void... voids) {
        RequestHelp requestHelp = new RequestHelp();
        new RequestHelpService(requestHelp, activity, AppVariables.REQUEST_USER_HELP,
                RequestHandler.POST, new JsonUtils<RequestHelp>().process(requestHelp)).makeRequest();
        return null;
    }

    @Override
    protected void onPostExecute(final MessageResponse messageResponse) {
        if (dialog.isShowing())
            dialog.dismiss();
        if(messageResponse == null) {

        }else{
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, messageResponse.getMsg(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    class RequestHelpService extends RequestHandler<RequestHelp>{

        protected RequestHelpService(RequestHelp requestHelp, Context context, String URL, String methodConnection, String params) {
            super(requestHelp, context, URL, methodConnection, params);
        }

        @Override
        protected void error(MessageResponse messageResponse) {
            onPostExecute(messageResponse);
        }

        @Override
        protected void close(RequestHelp requestHelp) {

        }
    }
}
