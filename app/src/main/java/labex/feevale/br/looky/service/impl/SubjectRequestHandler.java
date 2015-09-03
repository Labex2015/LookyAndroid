package labex.feevale.br.looky.service.impl;

import android.content.Context;

import java.util.List;

import labex.feevale.br.looky.model.Subject;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.utils.CallbackTask;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by grimmjowjack on 8/27/15.
 */
public class SubjectRequestHandler extends RequestHandler<List<Subject>> {

    CallbackTask<List<Subject>> callback;

    public SubjectRequestHandler(List<Subject> subjects, Context context, CallbackTask callback) {
        super(subjects, context, AppVariables.SUBJECTS, RequestHandler.GET);
        this.callback = callback;
    }

    @Override
    protected void error(MessageResponse response) {
        callback.error(response);
    }

    @Override
    protected List<Subject> postExecuteCore(String response) {
        return new JsonUtils().JsonToListSubjects(response);
    }

    @Override
    protected void close(List<Subject> subjects) {
        callback.success(subjects);
    }
}
