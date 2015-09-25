package labex.feevale.br.looky.gcm;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import labex.feevale.br.looky.service.utils.GCMVariables;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GCMService extends Service{

    private GoogleCloudMessaging gcm;
    private String key;

    public GCMService() {
    }

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
                    gcm = GoogleCloudMessaging.getInstance(getApplication().getApplicationContext());
                    if(new AppHelp(getApplication().getApplicationContext()).validateConnection()) {
                        if (gcm == null)
                            gcm = GoogleCloudMessaging.getInstance(getApplication().getApplicationContext());

                        key = gcm.register(GCMVariables.PROJECT_NUMBER);
                        new SharedPreferencesUtils().saveUserKey(key, getApplicationContext());
                        L.output(key);
                    }
                }catch (IOException ex) {
                    L.output(ex);
                }catch (Exception e){
                    L.output(e);
                }
                return null;
            }
        }.execute();

        return Service.START_FLAG_REDELIVERY;
    }
}
