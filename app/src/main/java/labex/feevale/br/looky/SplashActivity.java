package labex.feevale.br.looky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import labex.feevale.br.looky.dao.SubjectDao;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.impl.LoadSubjectService;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by grimmjowjack on 8/28/15.
 */
public class SplashActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (new SubjectDao().ïsEmpty()) {
            startService(new Intent(Intent.ACTION_SYNC, null, this, LoadSubjectService.class));
            finish();
        }
        User user = new SharedPreferencesUtils().getUser(this);
        if(user  != null) {
            if (user.getProfileStatus() == User.COMPLETE){
                ((LookyApplication) getApplication()).loadMainActivity();
            }else{
                ((LookyApplication) getApplication()).loadSecondPhaseLogin();
            }
            finish();
        }else{
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
