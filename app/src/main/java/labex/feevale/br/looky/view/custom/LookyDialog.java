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
import labex.feevale.br.looky.view.dialogs.DialogActions;

/**
 * Created by grimmjowjack on 9/22/15.
 */
public class LookyDialog extends DialogFragment implements View.OnClickListener {

    private String title;
    private String message;
    private DialogActions actions;
    private ImageButton cancelButton, confirmButton;

    private Boolean overrideActions;

    public LookyDialog(String title, String message, DialogActions actions) {
        this.title = title;
        this.message = message;
        this.actions = actions;
    }

    public LookyDialog(String title, String message, DialogActions actions,Boolean overrideActions) {
        this(title, message, actions);
        this.overrideActions = overrideActions;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.looky_dialog, container, false);
        ((TextView)view.findViewById(R.id.dialogTitleTextView)).setText(title);
        ((TextView)view.findViewById(R.id.dialogMessageTextView)).setText(message);
        if(overrideActions){
            ((TextView)view.findViewById(R.id.dialogExtraTextView)).setText(R.string.dialog_req_global_question_remove);

            cancelButton = (ImageButton) view.findViewById(R.id.dialogBackButton);
            cancelButton.setVisibility(View.VISIBLE);

            confirmButton = (ImageButton) view.findViewById(R.id.dialogDeleteButton);
            confirmButton.setVisibility(View.VISIBLE);
        }else{
            ((TextView)view.findViewById(R.id.dialogExtraTextView)).setText(R.string.dialog_req_global_question);

            cancelButton = (ImageButton) view.findViewById(R.id.dialogCancelButton);
            cancelButton.setVisibility(View.VISIBLE);

            confirmButton = (ImageButton) view.findViewById(R.id.dialogConfirmButton);
            confirmButton.setVisibility(View.VISIBLE);
        }
        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == cancelButton.getId()) {
            actions.cancelAction();
            this.dismiss();
        }else if(view.getId() == confirmButton.getId())
            actions.confirmAction();
            this.dismiss();
    }
}
