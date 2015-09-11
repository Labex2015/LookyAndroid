package labex.feevale.br.looky;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import java.io.IOException;

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
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    private ProgressBar progressBar;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        findViewById(R.id.signInButtonGoogle).setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        disconnectGoogle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
        }
            mIsResolving = false;
            mGoogleApiClient.connect();
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
                .addScope(new Scope(Scopes.EMAIL))
                .build();
        mShouldResolve = true;
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
        mShouldResolve = false;
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (currentPerson != null) {
            user = new User();
            user.setName(currentPerson.getName().getFormatted());
            user.setUsername(currentPerson.getNickname());
            user.setPicturePath(currentPerson.getImage().getUrl());
            user.setDescription(currentPerson.getAboutMe());
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
        } /*else {
            // Show the signed-out UI
            //TODO: showSignedOutUI();
        }*/
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {
        L.output(loadPeopleResult);
    }

    @Override
    public void success(User user) {
        showProgressing(false);
        if(user.getDegree() != null && user.getSemester() > 0)
            finishMainActivity();
    }

    @Override
    public void error(MessageResponse response) {
        showProgressing(false);
        new SharedPreferencesUtils().clear(LoginActivity.this);
        if(response != null)
            Toast.makeText(LoginActivity.this, response.getMsg(), Toast.LENGTH_LONG).show();
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

    private void notifyUserInvalid(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }


    private void showProgressing(Boolean show) {
        if(show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    private void saveUser(User user) {
        new SharedPreferencesUtils().saveUser(this, user);
    }

    private void finishMainActivity() {
        ((LookyApplication)getApplicationContext()).loadMainActivity();
        disconnectGoogle();
        finish();
    }



    private class GetIdTokenTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            showProgressing(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Account account = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
            String scopes = AppVariables.GOOGLE_SCOPE;
            try {
                String ID =  GoogleAuthUtil.getToken(getApplicationContext(), account, scopes);
                user.setAccountID(ID);
                new SignInService(user,LoginActivity.this, AppVariables.URL_SIGN_IN_GOOGLE,
                                  new JsonUtils().UserToJson(user)).makeRequest();
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

}
