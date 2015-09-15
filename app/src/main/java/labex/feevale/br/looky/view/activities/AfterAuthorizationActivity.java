package labex.feevale.br.looky.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import labex.feevale.br.looky.MainActivity;
import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;

/**
 * Created by grimmjowjack on 9/10/15.
 */
public class AfterAuthorizationActivity extends AppCompatActivity
                                        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private User user;

    private Button saveButton;
    private AutoCompleteTextView degreeAutoComp;
    private Spinner semesterSpinner;

    public AfterAuthorizationActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_authorization_activity);
        degreeAutoComp = (AutoCompleteTextView) findViewById(R.id.signinDegreeAutoComplete);
        semesterSpinner = (Spinner) findViewById(R.id.semesterSignInSpinner);
        saveButton = (Button) findViewById(R.id.finishSignInButton);
        TextView notification = (TextView) findViewById(R.id.labelNotificationLoginNome);

        user = new SharedPreferencesUtils().getUSer(this);
        notification.setText(notification.getText().toString().replace("#", user.getUsername()));
        saveButton.setOnClickListener(this);
        semesterSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        if(validateItems()){
            new SharedPreferencesUtils().saveUser(this, user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validateItems() {
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        user.setSemester(new Integer(parent.getItemAtPosition(position).toString()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        user.setSemester(1);
    }
}
