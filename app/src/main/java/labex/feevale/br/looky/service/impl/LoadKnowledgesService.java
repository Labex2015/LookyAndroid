package labex.feevale.br.looky.service.impl;

import android.content.Context;

import java.util.List;

import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by pablo on 7/16/15.
 */
public class LoadKnowledgesService extends RequestHandler<List<Knowledge>> {


    protected LoadKnowledgesService(List<Knowledge> knowledges, Context context, String URL, String methodConnection, String params) {
        super(knowledges, context, URL, methodConnection, params);
    }

    protected LoadKnowledgesService(List<Knowledge> knowledges, Context context, String URL, String methodConnection) {
        super(knowledges, context, URL, methodConnection);
    }

    @Override
        protected void error(MessageResponse msg) {
            if(msg != null){
                messageResponse.setMsg(msg.getMsg());
                messageResponse.setStatus(false);
            }
        }

        @Override
        protected void close(List<Knowledge> knowledges) {
            knowledges.addAll(knowledges);
        }
}
