package labex.feevale.br.looky.view.actions;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.mod.GlobalMod;
import labex.feevale.br.looky.service.mod.ResponseMod;
import labex.feevale.br.looky.service.utils.HttpActions;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by grimmjowjack on 9/23/15.
 */
public class RequestGlobalFragmentActions {

    private Activity activity;
    private GlobalMod globalMod;
    private User user;
    private HttpActions<MessageResponse> httpActions;
    private MessageResponse response = new MessageResponse();

    public RequestGlobalFragmentActions(Activity activity, User user, GlobalMod globalMod,HttpActions<MessageResponse> httpActions) {
        this.activity = activity;
        this.globalMod = globalMod;
        this.httpActions = httpActions;
        this.user = user;
    }

    public void removeRequestGlobalHelp() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHandler.USER_PARAM, String.valueOf(user.getId()));
        headers.put(RequestHandler.TOKEN_PARAM, String.valueOf(new SharedPreferencesUtils().getToken(activity)));
        headers.put(RequestHandler.ITEM_PARAM, String.valueOf(globalMod.idRequestHelp));

        new RemoveRequestHelpTask(headers).execute();
    }

    public void answerRequestHelp() {
        new RespondRequestHelpTask().execute();
    }


    class RemoveRequestHelpTask extends AsyncTask<Void, Void, Void>{

        private HashMap params;
        public RemoveRequestHelpTask(HashMap params) {
            this.params = params;
        }

        @Override
        protected void onPreExecute() {
            ((MainActivity)activity).manageProgressView(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            new RequestGlobalHandler(new MessageResponse(),activity,
                                                AppVariables.URL_GLOBAL_REQUEST_HELP,
                                                RequestHandler.DELETE, null, params).makeRequest();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((MainActivity)activity).manageProgressView(true);
            if(response.getStatus())
                httpActions.afterDelete(response);
            else
                httpActions.fail(response);
        }
    }


    class RespondRequestHelpTask extends AsyncTask<Void, Void, Void>{

        private String url;
        private ResponseMod responseMod;

        public RespondRequestHelpTask() {
            this.url = AppVariables.RESPOND_USER_REQUEST.replace(AppVariables.TAG_IDUSER,
                                                                 String.valueOf(user.getId()));
        }

        @Override
        protected void onPreExecute() {
            ((MainActivity)activity).manageProgressView(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            responseMod = new ResponseMod(globalMod.idRequestHelp, null,true);
            String params = new JsonUtils<ResponseMod>().process(responseMod);
            new RequestGlobalHandler(new MessageResponse(),
                                     activity,url,RequestHandler.PUT, params, null).makeRequest();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((MainActivity)activity).manageProgressView(true);
            if(response.getStatus())
                httpActions.afterInsertUpdate(response);
            else
                httpActions.fail(response);
        }
    }

    class RequestGlobalHandler extends RequestHandler<MessageResponse>{

        protected RequestGlobalHandler(MessageResponse messageResponse,
                                       Context context, String URL, String methodConnection) {
            super(messageResponse, context, URL, methodConnection);
        }

        protected RequestGlobalHandler(MessageResponse messageResponse,
                                       Context context, String URL, String methodConnection,
                                       String params, HashMap headerParams) {
            super(messageResponse, context, URL, methodConnection, params, headerParams);
        }

        @Override
        protected void error(MessageResponse messageResponse) {
            response = messageResponse;
        }

        @Override
        protected MessageResponse postExecuteCore(String response) {
            entity.setMsg(response);
            entity.setStatus(true);
            return entity;
        }

        @Override
        protected void close(MessageResponse messageResponse) {
            response = messageResponse;
        }
    };

}
