package labex.feevale.br.looky.service;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import labex.feevale.br.looky.service.impl.LoadKnowledgeFragment;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by pablo on 7/17/15.
 */
public abstract class RequestHandlerTask<Entity> extends RequestHandler implements Runnable{

    public Thread thread;
    public Handler handler;
    public Message message = new Message();

    protected RequestHandlerTask(Entity entity, Context context, String URL, String methodConnection, String params, Handler handler) {
        super(entity, context, URL, methodConnection, params);
        this.handler = handler;
    }

    protected RequestHandlerTask(Entity entity, Context context, String URL, String methodConnection, Handler handler) {
        super(entity, context, URL, methodConnection);
        this.handler = handler;
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        makeRequest();
    }

    @Override
    protected void error(MessageResponse messageResponse) {
        message.arg1 = LoadKnowledgeFragment.ERROR;
        Bundle bundle = new Bundle();
        bundle.putString("MSG", messageResponse.getMsg());
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
