package labex.feevale.br.looky.view.adapters;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.Interaction;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.custom.RoundedImageView;
import labex.feevale.br.looky.view.fragments.EvaluationFragment;
import labex.feevale.br.looky.view.fragments.InteractionsFragment;

/**
 * Created by 0126128 on 15/06/2015.
 */
public class InteractionsPendingListAdapter extends BaseAdapter implements OnItemClickListener{

    private List<Interaction> interactionList;
    private Activity activity;
    private User me;
    private Interaction interaction;

    public InteractionsPendingListAdapter(List<Interaction> interactionList, Activity activity) {
        this.interactionList = new ArrayList<Interaction>();
        this.interactionList.addAll(interactionList);
        this.activity = activity;
        me = new SharedPreferencesUtils().getUSer(activity);
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
                interaction.getRequestHelp().getHelper().getUsername() :
                interaction.getRequestHelp().getRequester().getUsername();
        name.setText(anotherName);
        requestLabel.setText(interaction.getRequest());

        return view;
    }

    public void removeInteraction(){

        interactionList.remove(interaction);
        interaction = null;
        this.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        interaction = interactionList.get(i);
        ((MainActivity)activity).changeFragment(new EvaluationFragment(interaction.getId(),
                me.getId(), new TaskExtraAction() {
            @Override
            public void onPostExecuteSuccess(Object[] params) {
                Toast.makeText(activity, activity.getString(R.string.eval_sent_message_end),
                        Toast.LENGTH_LONG).show();
                removeInteraction();
                ((MainActivity)activity).changeFragment(new InteractionsFragment(
                        new InteractionsPendingListAdapter(interactionList, activity)));
            }

            @Override
            public void onPostExecuteError(Object param) {
                Toast.makeText(activity, ((MessageResponse)param).getMsg(),
                        Toast.LENGTH_LONG).show();
            }
        }));
    }
}
