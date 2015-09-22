package labex.feevale.br.looky.service.impl;

import android.app.Activity;
import android.os.AsyncTask;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.utils.HttpActions;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by pablo on 7/22/15.
 */
public class ManagerKnowledge extends AsyncTask<Void, Void, Void>{

    public static final String DELETE = "DELETE";
    public static final String INSERT = "INSERT";
    public static final String LIST = "LIST";

    private String type;
    private Activity activity;
    private HttpActions httpActions;
    private Knowledge knowledge;

    private MessageResponse messageResponse;

    public ManagerKnowledge(String type, Activity activity, HttpActions httpActions){
        this.type = type;
        this.activity = activity;
        this.httpActions = httpActions;
    }

    public ManagerKnowledge(String type, Activity activity, HttpActions httpActions, Knowledge knowledge){
        this.type = type;
        this.activity = activity;
        this.httpActions = httpActions;
        this.knowledge = knowledge;
    }

    @Override
    protected void onPreExecute() {
        ((MainActivity)activity).manageProgressView(false);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(type.equals(INSERT)){
            new ProcessKnowledgeRequest<Knowledge>(knowledge, activity, RequestHandler.POST,
                    new JsonUtils<Knowledge>().KnowledgeJson(knowledge)) {
                @Override
                protected void close(final Object object) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            httpActions.afterInsertUpdate(object);
                        }
                    });
                }
            }.makeRequest();
        }else if(type.equals(DELETE)){
            new ProcessKnowledgeRequest<Knowledge>(activity,knowledge.getArea(),
                    new SharedPreferencesUtils().getUser(activity).getId()) {
                @Override
                protected void close(Object object) {
                    messageResponse = new MessageResponse("Removido com sucesso!", true);
                    httpActions.afterDelete(messageResponse);
                }

                @Override
                protected Object postExecuteCore(String response) {
                    return null;
                }
            }.makeRequest();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ((MainActivity)activity).manageProgressView(true);
    }

    abstract class ProcessKnowledgeRequest<Entity> extends RequestHandler {

        public ProcessKnowledgeRequest(Entity entity, Activity context, String methodConnection, String params) {
            super(knowledge, context, AppVariables.KNOWLEDGE, methodConnection, params);
        }

        public ProcessKnowledgeRequest(Activity context, Long idArea, Long idUser) {
            super(null, context, AppVariables.KNOWLEDGE_REMOVE.replace(AppVariables.TAG_ID_KNOWLEDGE,
                    idArea+"").replace(AppVariables.TAG_IDUSER, idUser.toString()), RequestHandler.DELETE);
        }

        public ProcessKnowledgeRequest(Entity entity, Activity context, Long idUser) {
            super(knowledge, context, AppVariables.KNOWLEDGE_USER.replace(AppVariables.TAG_IDUSER,
                    idUser.toString()), RequestHandler.GET);
        }

        @Override
        protected void error(MessageResponse messageResponse) {
            httpActions.fail(messageResponse);
        }
    }
}
