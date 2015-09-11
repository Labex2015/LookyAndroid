package labex.feevale.br.looky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import labex.feevale.br.looky.dao.SubjectDao;
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
        if(new SharedPreferencesUtils().getUSer(this) != null) {
            if (new SubjectDao().ïsEmpty()) {
                startService(new Intent(Intent.ACTION_SYNC, null, this, LoadSubjectService.class));
                finish();
            } else {
                if (getApplication() instanceof LookyApplication)
                    ((LookyApplication) getApplication()).loadMainActivity();
                finish();
            }
        }else{
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}
