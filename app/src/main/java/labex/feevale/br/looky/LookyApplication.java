package labex.feevale.br.looky;

import android.content.Intent;

import com.orm.SugarApp;

import labex.feevale.br.looky.utils.L;

/**
 * Created by grimmjowjack on 8/7/15.
 */
public class LookyApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
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
}
