package labex.feevale.br.looky.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.util.Date;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.service.impl.CloseInteractionTask;
import labex.feevale.br.looky.service.mod.EvaluationMod;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.view.BaseFragment;
import labex.feevale.br.looky.view.dialogs.DialogActions;
import labex.feevale.br.looky.view.dialogs.DialogMaker;

/**
 * Created by 0126128 on 18/06/2015.
 */
public class EvaluationFragment extends BaseFragment implements DialogActions{

    private Long idInteraction;
    private Long idUser;
    private EvaluationMod mod;

    private RatingBar helpRating;
    private RatingBar answerRating;
    private EditText commentEditText;

    private Button finishButton;

    private TaskExtraAction extraAction;

    public EvaluationFragment(Long idInteraction, Long idUser, TaskExtraAction extraAction) {
        this.idInteraction = idInteraction;
        this.idUser = idUser;
        this.extraAction = extraAction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.evaluation_fragment, container, false);

        helpRating = (RatingBar)view.findViewById(R.id.ratingHelp);
        answerRating = (RatingBar)view.findViewById(R.id.ratingAnswer);
        commentEditText = (EditText) view.findViewById(R.id.commentEvalEditText);
        finishButton = (Button) view.findViewById(R.id.saveEvalButton);

        finishButton.setOnClickListener(finishInteraction());

        return view;
    }

    private View.OnClickListener finishInteraction() {
        final DialogActions actions = this;
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogMaker(getActivity().getString(R.string.eval_finish_title_dialog),
                        getActivity().getString(R.string.eval_finish_message_dialog), actions)
                        .createDialog(getActivity()).show();
            }
        };
    }

    @Override
    public void cancelAction() {}

    @Override
    public void confirmAction() {
        mod = new EvaluationMod();
        mod.setAvaliated(new Date());
        mod.setAnswerPoints(answerRating.getProgress());
        mod.setHelpPoints(helpRating.getProgress());
         new CloseInteractionTask(mod, idUser, idInteraction, getActivity(),
                 CloseInteractionTask.TASK, extraAction).execute();
    }


}
