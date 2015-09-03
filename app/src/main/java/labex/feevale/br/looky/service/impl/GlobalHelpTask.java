package labex.feevale.br.looky.service.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.GlobalHelp;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.utils.ExtraAction;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 23/06/2015.
 */
public class GlobalHelpTask extends AsyncTask<Void, Void, Void> implements ExtraAction<MessageResponse>{

    public static final int TASK = 1;
    public static final int SERVICE = 2;

    private int type;
    private Activity activity;
    private GlobalHelp globalHelp;
    private TaskExtraAction<MessageResponse> extraAction;

    private ProgressDialog dialog;
    private MessageResponse message;


    public GlobalHelpTask(Activity activity, GlobalHelp globalHelpTask, int type,
                          TaskExtraAction<MessageResponse> extraAction) {
        this.activity = activity;
        this.globalHelp = globalHelpTask;
        this.type = type;
        this.extraAction = extraAction;
    }

    @Override
    protected void onPreExecute() {
        if(type == TASK){
            dialog = new ProgressDialog(activity);
            dialog.setMessage(activity.getString(R.string.eval_sent_message_progress));
            dialog.show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new SendGlobalRequestHelp(activity, globalHelp,this).makeRequest();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(type == TASK && dialog.isShowing())
            dialog.dismiss();

        if(message != null && message.getStatus())
            extraAction.onPostExecuteSuccess(message);
        else
            extraAction.onPostExecuteError(message);

    }

    @Override
    public void executeAction(MessageResponse[] params) {
        this.message = params[0];
    }

    class SendGlobalRequestHelp extends RequestHandler<String>{

        private ExtraAction<MessageResponse> extra;


        protected SendGlobalRequestHelp(Activity activity, GlobalHelp globalHelp, ExtraAction<MessageResponse> extra) {
            super("", activity, AppVariables.URL_SEND_GLOBAL_REQUEST_HELP, RequestHandler.POST,
                    new JsonUtils<GlobalHelp>(globalHelp).process(globalHelp));
            this.extra = extra;
        }

        @Override
        protected void error(MessageResponse messageResponse) {
           extra.executeAction(messageResponse);
        }

        @Override
        protected void close(String s) {
            extra.executeAction(new MessageResponse("Pedido cadastrado com sucesso!", true));
        }
    }
}
