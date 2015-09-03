package labex.feevale.br.looky.view.listener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;

import java.util.Arrays;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.model.Interaction;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.impl.RequestInteractionsTask;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.adapters.InteractionsListAdapter;
import labex.feevale.br.looky.view.adapters.InteractionsPendingListAdapter;
import labex.feevale.br.looky.view.fragments.InteractionsFragment;

/**
 * Created by 0126128 on 25/06/2015.
 */
public class TabEventListenersImpl implements ActionBar.TabListener {

    public static final int SEARCH_HELP = 0;
    public static final int INTERACTIONS = 1;
    public static final int PENDING = 2;

    private int position;
    private Activity activity;

    public TabEventListenersImpl(int position, Activity activity) {
        this.position = position;
        this.activity = activity;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        User user = new SharedPreferencesUtils().getUSer(activity);

        switch (position){
            case SEARCH_HELP: ((MainActivity)activity).loadMainScreen();
                break;
            case INTERACTIONS:
                new RequestInteractionsTask(activity, AppVariables.URL_LIST_INTERACTIONS
                        .replace(AppVariables.TAG_IDUSER, user.getId().toString()), new TaskExtraAction<Interaction>(){
                    @Override
                    public void onPostExecuteSuccess(Interaction... params) {
                        ((MainActivity)activity).changeFragment(new InteractionsFragment
                                (new InteractionsListAdapter(Arrays.asList(params), activity)));
                    }
                    @Override
                    public void onPostExecuteError(Interaction param) {}
                }).execute();
                break;
            case PENDING:
                new RequestInteractionsTask(activity, AppVariables.URL_LIST_PENDING_INTERACTIONS
                        .replace(AppVariables.TAG_IDUSER, user.getId().toString()), new TaskExtraAction<Interaction>(){
                    @Override
                    public void onPostExecuteSuccess(Interaction... params) {
                        ((MainActivity)activity).changeFragment(new InteractionsFragment
                                (new InteractionsPendingListAdapter(Arrays.asList(params), activity)));
                    }
                    @Override
                    public void onPostExecuteError(Interaction param) {}
                }).execute();
                break;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
}
