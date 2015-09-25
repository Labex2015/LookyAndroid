package labex.feevale.br.looky;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.impl.SignInService;
import labex.feevale.br.looky.service.utils.CallbackTask;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by grimmjowjack on 9/6/15.
 */
public class LoginActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks,
                                                                 GoogleApiClient.OnConnectionFailedListener,
                                                                 View.OnClickListener, ResultCallback<People.LoadPeopleResult>,
                                                                 CallbackTask<User>{
    private static final int RC_SIGN_IN = 0;
    private static final int GOOGLE = 1;

    private GoogleApiClient mGoogleApiClient;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;

    private LoginButton faceBookLoginButton;
    private CallbackManager callbackManager;

    private ProgressDialog progressDialog;

    private User user;
    private int loginAction;
    private ProfileTracker facebookProfileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.login_activity);
        findViewById(R.id.signInButtonGoogle).setOnClickListener(this);
        faceBookLoginButton = (LoginButton)findViewById(R.id.signInFaceBookButton);

        callbackManager = CallbackManager.Factory.create();
        faceBookLoginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        faceBookLoginButton.registerCallback(callbackManager, facebookCallback());
    }

    @Override
    protected void onStop() {
        super.onStop();
        disconnectGoogle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && loginAction == GOOGLE) {
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }
            mIsResolving = false;
            mGoogleApiClient.connect();
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signInButtonGoogle) {
            onSignInGoogleClicked();
        }
    }

    private void onSignInGoogleClicked() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.PLUS_ME))
                .build();

        mShouldResolve = true;
        loginAction = GOOGLE;
        mGoogleApiClient.connect();
    }

    private void startSecondPhase(User user) {
        manageProgress(false);
        disconnectGoogle();
        new SharedPreferencesUtils().saveUser(this, user);
        ((LookyApplication)getApplicationContext()).loadSecondPhaseLogin();
        finish();
    }

    @Override
    public void error(final MessageResponse response) {
        manageProgress(false);
        new SharedPreferencesUtils().clear(LoginActivity.this);
        showMessage(false, response != null ? response.getMsg() : null);
    }


    private void notifyUserInvalid(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    private void showMessage(final Boolean show, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                manageProgress(false);
                if (message != null)
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveUser(User user) {
        new SharedPreferencesUtils().saveUser(this, user);
    }

    private void finishMainActivity() {
        manageProgress(false);
        new SharedPreferencesUtils().saveUser(this, user);
        ((LookyApplication)getApplicationContext()).loadMainActivity();
        disconnectGoogle();
        finish();
    }


    /** FACEBOOK CONFIGURATION*/
    private FacebookCallback<LoginResult> facebookCallback(){
        return new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends"));
                facebookProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile old, Profile profile) {
                        user = new User();
                        user.setAccountType(User.FACEBOOK);
                        user.setName(profile.getName());
                        user.setPicturePath("http://graph.facebook.com/" + profile.getId() + "/picture?type=large");
                        user.setAccountID(profile.getId());
                        user.setToken(loginResult.getAccessToken().getToken());

                        new SharedPreferencesUtils().saveToken(user.getToken(),LoginActivity.this);
                        new FacebookLoginTask(loginResult).execute();
                        facebookProfileTracker.stopTracking();
                    }
                };
                facebookProfileTracker.startTracking();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException e) {}
        };
    }

    class FacebookLoginTask extends AsyncTask<Void, Void, Void>{


        LoginResult loginResult;

        public FacebookLoginTask(LoginResult loginResult) {
            this.loginResult = loginResult;
        }

        @Override
        protected void onPreExecute() {
            manageProgress(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            try {
                                String email = jsonObject.getString("email");
                                if (email != null)
                                    user.setUsername(email);
                                else {
                                    String name = jsonObject.getString("name");
                                    user.setUsername(name.replaceAll(" ", "").trim());
                                }
                                user.setDeviceKey(new SharedPreferencesUtils().getUserKey(LoginActivity.this));
                                String param = new JsonUtils().userToJson(user);
                                new SignInService(user,LoginActivity.this, AppVariables.URL_SIGN_IN_FACEBOOK,param,
                                  LoginActivity.this).makeRequest();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAndWait();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            manageProgress(false);
        }
    }

    /** FACEBOOK CONFIGURATION - END*/

    /** GOOGLE CONFIGURATION*/

    @Override
    public void onConnected(Bundle bundle) {
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
        mShouldResolve = false;
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (currentPerson != null) {

            user = new User();
            user.setAccountType(User.GOOGLE);
            user.setName(currentPerson.getDisplayName());
            user.setPicturePath(currentPerson.getImage().getUrl());
            user.setDescription(currentPerson.getAboutMe());
            user.setUsername(currentPerson.getNickname());

            new GetIdTokenTask().execute();
        }else{
            error(new MessageResponse("Problemas ao listar usuário da conta",false));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    L.output("Could not resolve ConnectionResult." + e.getMessage());
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                showErrorDialog(connectionResult);
            }
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {
        L.output(loadPeopleResult);
    }

    @Override
    public void success(User user) {
        showMessage(false, null);
        if(user.getDegreeID() > 0 && user.getSemester() > 0)
            finishMainActivity();
        else
            startSecondPhase(user);

    }

    private void showErrorDialog(ConnectionResult connectionResult) {
        final int errorCode = connectionResult.getErrorCode();

        if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
            GooglePlayServicesUtil.getErrorDialog(errorCode, this, RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mShouldResolve = false;
                            L.output("Error "+errorCode);
                            disconnectGoogle();
                        }
                    }).show();
        } else {
            String errorString = "Error: "+ errorCode;
            Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();

            mShouldResolve = false;
        }
    }

    private void disconnectGoogle() {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    private class GetIdTokenTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferencesUtils utils = new SharedPreferencesUtils();
            String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Account account = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
            String scopes = AppVariables.GOOGLE_SCOPE;
            try {
                String ID =  GoogleAuthUtil.getToken(getApplicationContext(), account, scopes);
                user.setToken(ID);

                utils.saveToken(ID, LoginActivity.this);

                user.setDeviceKey(utils.getUserKey(LoginActivity.this));
                String param = new JsonUtils().UserToJson(user);

                new SignInService(user,LoginActivity.this, AppVariables.URL_SIGN_IN_GOOGLE, param,
                                  LoginActivity.this).makeRequest();
            } catch (IOException e) {
                L.output("Error retrieving ID token."+e);
                return null;
            } catch (GoogleAuthException e) {
                L.output("Error retrieving ID token." + e);
                return null;
            }
            return null;
        }

    }

    /** GOOGLE CONFIGURATION - END */

    public void manageProgress(final Boolean status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressDialog != null && progressDialog.isShowing() && !status){
                    progressDialog.dismiss();
                    progressDialog = null;
                }else{
                    if(status) {
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage(getString(R.string.LOADING));
                        progressDialog.show();
                    }
                }
            }
        });
    }

}
