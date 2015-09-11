package labex.feevale.br.looky.service.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.SourceAreas;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.RequestHandlerTask;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.view.fragments.KnowledgeFragment;

/**
 * Created by pablo on 7/16/15.
 */
public class LoadKnowledgeFragment {

    public static int counter = 0;
    public static final int ERROR = 0, OK = 1;


    private Activity activity;
    private User user;

    private final List<Knowledge> knowledges = new ArrayList<>();
    private final SourceAreas areas = new SourceAreas();

    private ProgressDialog dialog;

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static Handler handler;
    private final BlockingQueue<Runnable> mDecodeWorkQueue;
    private static final int KEEP_ALIVE_TIME = 5;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private static ThreadPoolExecutor threadPoolExecutor;

    public LoadKnowledgeFragment(final Activity activity, User user) {
        this.user = user;
        this.activity = activity;
        ((MainActivity) activity).manageProgressView(false);
        mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                synchronized (this) {
                    if (msg.arg1 == OK) {
                        if (++counter == 2) {
                            ((MainActivity) activity).manageProgressView(true);
                            counter = 0;
                            ((MainActivity) activity)
                                    .changeFragment(new KnowledgeFragment(knowledges, areas.areas, areas.subjects));
                        }
                    } else {
                        cancelAll();
                        ((MainActivity) activity).manageProgressView(true);
                        String response = msg.getData().getString("MSG");
                        if (response != null)
                            Toast.makeText(activity, response, Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        threadPoolExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDecodeWorkQueue);
    }

    public void load() {
        threadPoolExecutor.execute(new KnowledgeRequestTask(knowledges, activity, handler, user.getId()));
        threadPoolExecutor.execute(new AreaRequestTask(areas, activity, handler));
    }

    public void cancelAll() {
        counter = 0;
        activity = null;
        if(threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
            threadPoolExecutor = null;
        }
    }

}

    class KnowledgeRequestTask extends RequestHandlerTask<List<Knowledge>> {
        List<Knowledge> param;
        protected KnowledgeRequestTask(List<Knowledge> param, Context context, Handler handler, Long idUser) {
            super(param, context, AppVariables.KNOWLEDGE_USER.replace(AppVariables.TAG_IDUSER,idUser.toString()),
                    RequestHandler.GET, handler);
            this.param = param;
        }

        @Override
        protected Object postExecuteCore(String response) {
            return new JsonUtils().JsonToListKnowledges(response);
        }

        @Override
        protected void close(Object response) {
            if(response != null) {
                ((List<Knowledge>) param).addAll((List<Knowledge>) response);
                message.arg1 = LoadKnowledgeFragment.OK;
                handler.sendMessage(message);
            }
        }
    }



    class AreaRequestTask extends RequestHandlerTask<SourceAreas> {

        private SourceAreas referency;

        protected AreaRequestTask(SourceAreas entity, Context context, Handler handler) {
            super(entity, context, AppVariables.KNOWLEDGE_RESOURCES, RequestHandler.GET, handler);
            referency = entity;
        }

        @Override
        protected void close(Object response) {
            if(response != null) {
                referency.areas = ((SourceAreas) response).areas;
                referency.subjects = ((SourceAreas) response).subjects;
                message.arg1 = LoadKnowledgeFragment.OK;
                handler.sendMessage(message);
            }
        }
    }

