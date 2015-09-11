package labex.feevale.br.looky;

import android.app.Application;
import android.content.Intent;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import labex.feevale.br.looky.view.activities.AfterAuthorizationActivity;


/**
 * Created by grimmjowjack on 8/7/15.
 */
public class LookyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
//        Realm.setDefaultConfiguration(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void loadMainActivity() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivityIntent);
    }

    public void loadSecondPahseLogin(){
        Intent secondPhase = new Intent(this, AfterAuthorizationActivity.class);
        secondPhase.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(secondPhase);
    }
}
