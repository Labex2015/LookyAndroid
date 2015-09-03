package labex.feevale.br.looky.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Jeferson on 12/12/2014.
 */
public class AppHelp {
    Context mContext;

    public AppHelp(Context mContext) {
        this.mContext = mContext;
    }

    public  boolean validateConnection() {
        boolean connection;

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
            connection = true;
        } else {
            connection = false;
        }
        return connection;
    }
}
