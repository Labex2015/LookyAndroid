package labex.feevale.br.looky.view.dialogs;

import android.app.Activity;

/**
 * Created by PabloGilvan on 03/01/2015.
 */
public class CloseAppAction implements DialogActions {

    private Activity activity;

    public CloseAppAction(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void cancelAction() {

    }

    @Override
    public void confirmAction() {
        activity.finish();
    }
}
