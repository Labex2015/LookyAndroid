package labex.feevale.br.looky.gcm;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import labex.feevale.br.looky.service.utils.GCMVariables;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GCMService extends AsyncTask<Void, Void,Void>{

    private GoogleCloudMessaging gcm;
    private Activity activity;
    private TaskExtraAction<String> action;
    private String key;

    public GCMService(Activity activity, TaskExtraAction<String> action) {
        gcm = GoogleCloudMessaging.getInstance(activity);
        this.activity = activity;
        this.action = action;
    }

    @Override
    protected Void doInBackground(Void... voids){
        try {
            if(new AppHelp(activity).validateConnection()) {
                if (gcm == null)
                    gcm = GoogleCloudMessaging.getInstance(activity.getApplicationContext());

                key = gcm.register(GCMVariables.PROJECT_NUMBER);
                new SharedPreferencesUtils().saveUserKey(key, activity);
            }
        }catch (IOException ex) {
            Log.e("Error", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        if(key != null && key.length() > 5)
            action.onPostExecuteSuccess(key);
        else
            action.onPostExecuteError(key);
    }
}
