package labex.feevale.br.looky.view.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by PabloGilvan on 28/12/2014.
 */
public class DialogMaker {

    private AlertDialog dialog;
    private String title;
    private String message;
    private DialogActions dialogActions;

    public DialogMaker(String title, String message, DialogActions dialogActions) {
        this.title = title;
        this.message = message;
        this.dialogActions = dialogActions;
    }

    public AlertDialog createDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogActions.cancelAction();
                if(dialog.isShowing())
                    dialog.dismiss();
            }
        });
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogActions.confirmAction();
                if(dialog.isShowing())
                    dialog.dismiss();
            }
        });
        dialog = builder.create();
        return dialog;
    }
}
