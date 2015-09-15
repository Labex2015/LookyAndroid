package labex.feevale.br.looky.service.impl;

import android.content.Context;

import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.service.utils.CallbackTask;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by grimmjowjack on 9/10/15.
 */
public class SignInService extends RequestHandler<User> {

    private CallbackTask<User> callbackTask;

    public SignInService(User user, Context context, String url, String param, CallbackTask callbackTask) {
        super(user, context, url,
                RequestHandler.POST, param);
        this.callbackTask = callbackTask;
    }


    @Override
    protected void error(MessageResponse messageResponse) {
        callbackTask.error(messageResponse);
    }

    @Override
    protected void close(User user) {
        callbackTask.success(user);
    }
}
