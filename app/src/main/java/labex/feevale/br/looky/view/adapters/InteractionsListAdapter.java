package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Interaction;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.impl.CloseInteractionTask;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.custom.RoundedImageView;
import labex.feevale.br.looky.view.dialogs.DialogActions;
import labex.feevale.br.looky.view.dialogs.DialogMaker;
import labex.feevale.br.looky.view.fragments.EvaluationFragment;

/**
 * Created by 0126128 on 15/06/2015.
 */
public class InteractionsListAdapter extends BaseAdapter implements DialogActions,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private List<Interaction> interactionList;
    private Activity activity;
    private User me;
    private Interaction interaction;

    public InteractionsListAdapter(List<Interaction> interactionList, Activity activity) {
        this.interactionList = interactionList;
        this.activity = activity;
        me = new SharedPreferencesUtils().getUser(activity);
    }

    @Override
    public int getCount() {
        return this.interactionList.size();
    }

    @Override
    public Object getItem(int i) {
        return interactionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.interaction_list_item, null);

        RoundedImageView picture = (RoundedImageView) view.findViewById(R.id.anotherUserPictureRoundedImage);
        TextView name = (TextView)view.findViewById(R.id.anotherUserTextView);
        TextView requestLabel = (TextView) view.findViewById(R.id.requestHelpLabelTextView);

        Interaction interaction = interactionList.get(i);
        if(interaction.getPictureAnotherUser() != null)
            picture.setImageBitmap(BitmapFactory.decodeByteArray(
                    interaction.getPictureAnotherUser(), 0, interaction
                            .getPictureAnotherUser().length));
        String anotherName = interaction.getRequestHelp().getHelper().getId() != me.getId() ?
                interaction.getRequestHelp().getHelper().getUsername() : interaction.getRequestHelp().getRequester().getUsername();
        name.setText(anotherName);
        requestLabel.setText(interaction.getRequestHelp().getText());
        return view;
    }

    @Override
    public void cancelAction() {}

    @Override
    public void confirmAction() {
       if(interaction.getRequestHelp().getRequester().getId() == me.getId()){
           ((MainActivity)activity).changeFragment(new EvaluationFragment(interaction.getId(),
                   me.getId(), new TaskExtraAction<MessageResponse>(){

               @Override
               public void onPostExecuteSuccess(MessageResponse... params) {
                   ((MainActivity)activity).changeFragment(new Fragment());
               }

               @Override
               public void onPostExecuteError(MessageResponse param) {
                   Toast.makeText(activity,param.getMsg(), Toast.LENGTH_LONG).show();
               }
           }));
       }else{
           final BaseAdapter baseAdapter = this;
           new CloseInteractionTask(me.getId(), interaction.getId(), activity,
                   CloseInteractionTask.TASK, new TaskExtraAction<MessageResponse>() {
               @Override
               public void onPostExecuteSuccess(MessageResponse... params) {
                   interactionList.remove(interaction);
                   interaction = null;
                   baseAdapter.notifyDataSetChanged();
               }

               @Override
               public void onPostExecuteError(MessageResponse param) {
                   Toast.makeText(activity,param.getMsg(), Toast.LENGTH_LONG).show();
               }
           }).execute();
       }
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        interaction = interactionList.get(i);
        new DialogMaker(activity.getString(R.string.interactions_cancel_dialog),
                activity.getString(R.string.interactions_cancel_msg_dialog)
                        .replace("#_USER", interaction.getRequestHelp().getHelper().getId() != me.getId() ?
                                interaction.getRequestHelp().getHelper().getUsername() :
                                interaction.getRequestHelp().getRequester().getUsername()), this)
                .createDialog(activity).show();
        return true;
    }
}
