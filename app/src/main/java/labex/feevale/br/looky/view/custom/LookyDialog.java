package labex.feevale.br.looky.view.custom;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.service.utils.CallbackTask;
import labex.feevale.br.looky.view.dialogs.DialogActions;

/**
 * Created by grimmjowjack on 9/22/15.
 */
public class LookyDialog extends DialogFragment implements View.OnClickListener {

    private String title;
    private String message;
    private DialogActions actions;

    private ImageButton okButton, cancelButton;

    public LookyDialog(String title, String message, DialogActions actions) {
        this.title = title;
        this.message = message;
        this.actions = actions;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.looky_dialog, container, false);
        ((TextView)view.findViewById(R.id.dialogTitleTextView)).setText(title);
        ((TextView)view.findViewById(R.id.dialogMessageTextView)).setText(message);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
