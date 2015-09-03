package labex.feevale.br.looky;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import labex.feevale.br.looky.dao.SubjectDao;
import labex.feevale.br.looky.model.Subject;
import labex.feevale.br.looky.service.impl.LoadSubjectService;
import labex.feevale.br.looky.service.utils.CallbackTask;
import labex.feevale.br.looky.service.utils.ExtraAction;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by grimmjowjack on 8/28/15.
 */
public class SplashActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        if (new SubjectDao().ïsEmpty()) {
            startService(new Intent(Intent.ACTION_SYNC, null, this, LoadSubjectService.class));
        } else {
            if(getApplication() instanceof LookyApplication)
                ((LookyApplication)getApplication()).loadMainActivity();
        }
    }

}
