package labex.feevale.br.looky;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.Arrays;

import labex.feevale.br.looky.dao.SubjectDao;
import labex.feevale.br.looky.model.Interaction;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.impl.LoadKnowledgeFragment;
import labex.feevale.br.looky.service.impl.LoadSubjectService;
import labex.feevale.br.looky.service.impl.RequestInteractionsTask;
import labex.feevale.br.looky.service.utils.TaskExtraAction;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.utils.UserMock;
import labex.feevale.br.looky.view.adapters.InteractionsListAdapter;
import labex.feevale.br.looky.view.adapters.InteractionsPendingListAdapter;
import labex.feevale.br.looky.view.custom.RoundedImageView;
import labex.feevale.br.looky.view.fragments.InteractionsFragment;
import labex.feevale.br.looky.view.fragments.KnowledgeFragment;
import labex.feevale.br.looky.view.fragments.MainFragment;
import labex.feevale.br.looky.view.fragments.SearchHelpFragment;


public class MainActivity extends AppCompatActivity implements AppCompatCallback,
                                    NavigationView.OnNavigationItemSelectedListener{

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private static final char HOME = '1', SEARCH_HELP = '2', GLOBAL_HELP = '3',
                              CHAT = '4', HISTORY = '5', REQUESTS = '6',
                              PROFILE = '7', KNOWLEDGES = '8', PENDING = '9';

    public static int appState = 0;

    private int mNavItemId;
    private CharSequence mTitle;
    private User user;

    private final Handler mDrawerActionHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment mFragment;
    private Intent mService;
    private FragmentTransaction fragmentTransaction;

    private TextView usernameTextViewView, courseTexteView;
    private RoundedImageView userPicture;
    private InputMethodManager imm;
    private FloatingActionButton leftCenterButton;
    private static ProgressDialog progressDialog;


    public MainActivity() {}

    public MainActivity(Fragment fragment) {
        this();
        this.mFragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            setContentView(R.layout.activity_main);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            toolbar = (Toolbar) findViewById(R.id.action_bar_looky);
            toolbar.setTitle("");
            toolbar.setLogo(R.drawable.ic_launcher);
            setSupportActionBar(toolbar);

           // if (savedInstanceState == null) {
                mNavItemId = R.id.drawer_home;
           /* } else {
                mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
            }*/

            NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getMenu().findItem(mNavItemId).setChecked(true);

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.action_logout,
                    R.string.action_logout);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();

//        navigate(navigationView.getMenu().getItem(mNavItemId).getNumericShortcut());

            user = new SharedPreferencesUtils().getUSer(this);
            if (user != null) {
                usernameTextViewView = (TextView) findViewById(R.id.usernameDrawerTextView);
                courseTexteView = (TextView) findViewById(R.id.userCourseDrawerTextView);

                usernameTextViewView.setText(user.getName());
                courseTexteView.setText(user.getDegree());
                //loadMainScreen();

                if(savedInstanceState == null)
                    changeFragment(new MainFragment());
            }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // getSupportFragmentManager().putFragment(outState,"mfragment", mFragment);
    }

    @Override
    public void onBackPressed() {
        if(mFragment == null || mFragment instanceof MainFragment)
            finish();
        else
            super.onBackPressed();
    }

    public void setFragment(Fragment fragment){
        this.mFragment = fragment;
    }


    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        menuItem.setChecked(true);
        mNavItemId = menuItem.getNumericShortcut();

        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getNumericShortcut());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    private void navigate(char drawerItem) {

        switch (drawerItem){
            case HOME:        changeFragment(new MainFragment());
                break;
            case SEARCH_HELP: changeFragment(new SearchHelpFragment());
                break;
            case GLOBAL_HELP: ;
                break;
            case CHAT:        if(!(mFragment instanceof InteractionsFragment))loadInteractions();
                break;
            case PROFILE:
                break;
            case KNOWLEDGES:  if(!(mFragment instanceof KnowledgeFragment)) new LoadKnowledgeFragment(this, user).load();
                break;
            case PENDING: loadPending();
                break;
            case HISTORY:
                break;
            case REQUESTS:
                break;
        }
    }

    public void changeFragment(Fragment fragment){
        super.onPostResume();
        if(manageFragment(fragment)) {
            closeKeyboard();
            fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            this.invalidateOptionsMenu();

            if (fragment instanceof MainFragment)
                callMainMenuAnimation(false);
            else
                callMainMenuAnimation(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public static User getUserMock(){
        return UserMock.getUser();
    }

    public void loadMainScreen(){
        if(!(this.mFragment instanceof MainFragment))
            changeFragment(new MainFragment());
    }

    public void loadInteractions(){
        final User user = new SharedPreferencesUtils().getUSer(this);
        new RequestInteractionsTask(MainActivity.this, AppVariables.URL_LIST_INTERACTIONS
                        .replace(AppVariables.TAG_IDUSER, user.getId().toString()), new TaskExtraAction<Interaction>(){
                    @Override
                    public void onPostExecuteSuccess(Interaction... params) {
                        (MainActivity.this).changeFragment(new InteractionsFragment
                                (new InteractionsListAdapter(Arrays.asList(params), MainActivity.this)));
                    }
                    @Override
                    public void onPostExecuteError(Interaction param) {}
                }).execute();

    }

    public void loadPending(){
        final User user = new SharedPreferencesUtils().getUSer(this);
        new RequestInteractionsTask(MainActivity.this, AppVariables.URL_LIST_PENDING_INTERACTIONS
                .replace(AppVariables.TAG_IDUSER, user.getId().toString()), new TaskExtraAction<Interaction>(){
            @Override
            public void onPostExecuteSuccess(Interaction... params) {
                (MainActivity.this).changeFragment(new InteractionsFragment
                        (new InteractionsPendingListAdapter(Arrays.asList(params), MainActivity.this)));
            }
            @Override
            public void onPostExecuteError(Interaction param) {}
        }).execute();
    }

    public View.OnClickListener loadMainEventListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMainScreen();
            }
        };
    }

    private void callMainMenuAnimation(final Boolean hide){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(250);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                leftCenterButton.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

        });
    }

    public void closeKeyboard(){
        if(mFragment != null) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void manageProgressView(Boolean cancel, String... message){
        if(progressDialog != null && progressDialog.isShowing() && cancel){
            progressDialog.dismiss();
            progressDialog = null;
        }else{
            if(!cancel) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(message.length > 0 ? message[0] : getString(R.string.LOADING));
                progressDialog.show();
            }
        }
    }

    private Boolean manageFragment(Fragment solicitaded){
        if(mFragment == null)
            return true;
        if(solicitaded.getClass() == mFragment.getClass()){
            return false;
        }
        if(solicitaded instanceof MainFragment)
            if(appState++ == 1) {
                finish();
            }else{
                return true;
            }

        return true;
    }
}

