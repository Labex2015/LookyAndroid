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

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "UFeG5gH73W409oeS4kBo2Z3rm";
    public static final String TWITTER_SECRET = "LoIYh1PLDT0EpjuUmFvvPdZaCKXrec1B0GVZxRIEo8zWqdYAwx";


    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
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

    public void loadSecondPhaseLogin(){
        Intent secondPhase = new Intent(this, AfterAuthorizationActivity.class);
        secondPhase.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(secondPhase);
    }
}
