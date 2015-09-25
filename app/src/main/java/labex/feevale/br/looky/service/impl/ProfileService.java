package labex.feevale.br.looky.service.impl;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.RequestHandler;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by grimmjowjack on 9/24/15.
 */
public class ProfileService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    final SharedPreferencesUtils utils = new SharedPreferencesUtils();
                    final User user = utils.getUser(getApplication().getApplicationContext());
                    user.setToken(utils.getToken(getApplication().getApplicationContext()));
                    user.setDeviceKey(utils.getUserKey(getApplication().getApplicationContext()));

                    final String param = new JsonUtils<User>().userToJson(user);

                    new RequestHandler<String>("",getApplication().getApplicationContext(),
                                    AppVariables.URL_PROFILE, RequestHandler.PUT, param){

                        @Override
                        protected void error(MessageResponse messageResponse) {}

                        @Override
                        protected void close(String s) {
                            utils.saveState(true, getApplication().getApplicationContext());
                        }
                    }.makeRequest();

                }catch (Exception e){
                    L.output(e);
                }
                return null;
            }
        }.execute();

        return Service.START_FLAG_REDELIVERY;
    }
}
