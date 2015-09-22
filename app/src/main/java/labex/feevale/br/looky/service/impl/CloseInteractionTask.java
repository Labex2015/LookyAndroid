package labex.feevale.br.looky.service.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.mod.EvaluationMod;
import labex.feevale.br.looky.service.utils.ExtraAction;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.view.dialogs.DialogActions;
import labex.feevale.br.looky.view.dialogs.DialogMaker;
import labex.feevale.br.looky.view.fragments.ReqGlobalHelpFragment;

/**
 * Created by 0126128 on 18/06/2015.
 */
public class CloseInteractionTask extends AsyncTask<Void, Void, Void> implements ExtraAction<MessageResponse>{

    public static int SERVICE = 1, TASK = 2;

    private EvaluationMod mod;
    private Long idUser;
    private Long idInteraction;
    private Activity activity;

    private int type;

    private ProgressDialog dialog;
    private MessageResponse message;

    private TaskExtraAction taskExtraAction;

    public CloseInteractionTask(Long idUser, Long idInteraction, Activity activity, int type, TaskExtraAction taskExtraAction) {
        this.idUser = idUser;
        this.idInteraction = idInteraction;
        this.activity = activity;
        this.type = type;
        this.taskExtraAction = taskExtraAction;
    }

    public CloseInteractionTask(EvaluationMod mod, Long idUser, Long idInteraction,
                                Activity activity, int type, TaskExtraAction taskExtraAction) {
        this(idUser, idInteraction, activity, type,taskExtraAction);
        this.mod = mod;
    }

    @Override
    protected void onPreExecute() {
        if(type == TASK){
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Encerrando interacao....");
            dialog.show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if(mod != null){
            new CloseInteractionService(this,activity, new JsonUtils().process(mod)).makeRequest();
        }else{
            new CloseInteractionService(this, activity).makeRequest();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(type == TASK && dialog.isShowing())
            dialog.dismiss();

        if(message == null) {
            if(mod != null) {
                if (mod.getRating() > 3)
                    taskExtraAction.onPostExecuteSuccess();
                else
                    openGlobalRequestHelp();
            }
        }else
            taskExtraAction.onPostExecuteError(message);
    }

    @Override
    public void executeAction(MessageResponse... params) {
            if(params.length > 0)
                this.message = params[0];
    }


    class CloseInteractionService extends RequestHandler<MessageResponse>{

        private Context context;
        private ExtraAction<MessageResponse> extraAction;

        protected CloseInteractionService(ExtraAction extraAction, Context context, String params) {
            super(null, context, AppVariables.URL_CLOSE_INTERACTION.replace("#ID", idInteraction.toString())
                    .replace(AppVariables.TAG_IDUSER, idUser.toString()), RequestHandler.PUT, params);
            this.extraAction = extraAction;
            this.context =  context;
        }

        protected CloseInteractionService(ExtraAction extraAction, Context context) {
            super(null, context, AppVariables.URL_CLOSE_INTERACTION.replace("#ID", idInteraction.toString())
                    .replace(AppVariables.TAG_IDUSER, idUser.toString()), RequestHandler.PUT);
            this.extraAction = extraAction;
            this.context =  context;
        }

        @Override
        protected void error(MessageResponse messageResponse) {
            extraAction.executeAction(messageResponse);
        }

        @Override
        protected void close(MessageResponse o) {}

        @Override
        protected MessageResponse postExecuteCore(String response) {
            messageResponse = new MessageResponse();
            messageResponse.setMsg(response);
            return messageResponse;
        }
    }

    private void openGlobalRequestHelp(){
        new DialogMaker(activity.getString(R.string.dialog_req_global_title),
                activity.getString(R.string.dialog_req_global_message), new DialogActions(){
            @Override
            public void cancelAction() {
                try{
                    L.output(taskExtraAction);
                    taskExtraAction.onPostExecuteSuccess();
                }catch (Exception e){
                    L.output(e.getMessage());
                }

            }

            @Override
            public void confirmAction() {
                ((MainActivity)activity).changeFragment(
                        new ReqGlobalHelpFragment(idInteraction, null,new TaskExtraAction<MessageResponse>(){

                            @Override
                            public void onPostExecuteSuccess(MessageResponse... params) {
                                ((MainActivity)activity).loadMainScreen();
                            }
                            @Override
                            public void onPostExecuteError(MessageResponse param) {
                                Toast.makeText(activity, param.getMsg(), Toast.LENGTH_LONG);
                            }
                        }));
            }
        }).createDialog(activity).show();
    }
}
