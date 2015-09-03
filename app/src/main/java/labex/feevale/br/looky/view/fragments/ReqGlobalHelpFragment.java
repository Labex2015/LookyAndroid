package labex.feevale.br.looky.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.GlobalHelp;
import labex.feevale.br.looky.service.impl.GlobalHelpTask;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.dialogs.DialogActions;
import labex.feevale.br.looky.view.dialogs.DialogMaker;

/**
 * Created by 0126128 on 23/06/2015.
 */
public class ReqGlobalHelpFragment extends BaseFragment implements DialogActions {

    private GlobalHelp globalHelp;
    private Long idRequestHelp;

    private Button sendButton;
    private EditText descriptionEditText;
    private EditText titleEditText;
    private TaskExtraAction taskExtraAction;
    private List<EditTextWatcher> textWatchers = new ArrayList<EditTextWatcher>();

    public ReqGlobalHelpFragment(Long idRequestHelp, TaskExtraAction taskExtraAction) {
        this.globalHelp = globalHelp;
        this.idRequestHelp = idRequestHelp;
        this.taskExtraAction = taskExtraAction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.global_request_help_fragment, container, false);

        descriptionEditText = (EditText) view.findViewById(R.id.descriptionReqGlobalET);
        titleEditText = (EditText) view.findViewById(R.id.titleReqGlobalEditText);
        sendButton = (Button) view.findViewById(R.id.sendReqGlobalButton);

        textWatchers.add(new EditTextWatcher(7,
                getActivity().getString(R.string.form_req_global_error_field).replace("#C", 7+""),
                titleEditText));
        textWatchers.add(new EditTextWatcher(15,
                getActivity().getString(R.string.form_req_global_error_field).replace("#C", 15 + "")
                ,descriptionEditText));

        descriptionEditText.addTextChangedListener(textWatchers.get(1));
        titleEditText.addTextChangedListener(textWatchers.get(0));

        sendButton.setOnClickListener(sendEvaluations());
        return view;
    }

    private View.OnClickListener sendEvaluations(){
        final DialogActions actions = this;
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields()) {
                    new DialogMaker(getActivity().getString(R.string.form_req_global_dialog_title),
                            getActivity().getString(R.string.form_req_global_dialog_msg), actions)
                            .createDialog(getActivity()).show();
                }
            }
        };
    }

    private Boolean validateFields(){
        for(EditTextWatcher e : textWatchers){
            if(!e.getStatus())
                return false;
        }
        return true;
    }

    @Override
    public void cancelAction() {}

    @Override
    public void confirmAction() {
        loadHelp();
        new GlobalHelpTask(getActivity(), globalHelp,GlobalHelpTask.TASK,taskExtraAction)
                .execute();
    }

    private void loadHelp() {
        this.globalHelp = new GlobalHelp(idRequestHelp,
                titleEditText.getText().toString(),
                descriptionEditText.getText().toString());
    }

    class EditTextWatcher implements TextWatcher{

        private int size;
        private String message;
        private Boolean status = false;

        private EditText editText;

        public EditTextWatcher(int size, String message, EditText editText) {
            this.size = size;
            this.message = message;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length() < size)
                this.status = false;
            else {
                this.status = true;
            }
        }

        public Boolean getStatus(){
            if(!status)
                editText.setError(message);
            return status;
        }
    }
}
