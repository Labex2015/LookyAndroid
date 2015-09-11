package labex.feevale.br.looky.service.impl;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.LookyApplication;
import labex.feevale.br.looky.dao.SubjectDao;
import labex.feevale.br.looky.model.Subject;
import labex.feevale.br.looky.service.utils.CallbackTask;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by grimmjowjack on 8/27/15.
 */
public class LoadSubjectService extends Service implements CallbackTask<List<Subject>>{

    private List<Subject> subjects = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        final SubjectDao subjectDao = new SubjectDao();
        final CallbackTask callbackTask = this;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                new SubjectRequestHandler(subjects, getApplicationContext(),callbackTask).makeRequest();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                finish();
            }
        }.execute();
        return Service.START_FLAG_REDELIVERY;
    }


    @Override
    public void success(List<Subject> object) {
        subjects = object;
        try {
            SubjectDao subjectDao = new SubjectDao();
            subjectDao.saveSubjects(subjects.toArray(new Subject[subjects.size()]));
        }catch (Exception e){
            L.output(e.getMessage());
        }
    }

    @Override
    public void error(MessageResponse response) {}

    public void finish(){
        if(getApplication() instanceof LookyApplication)
            ((LookyApplication)getApplication()).loadMainActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
