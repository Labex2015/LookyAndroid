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
import labex.feevale.br.looky.dao.SubjectDao;
import labex.feevale.br.looky.model.Degree;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.utils.Observer;
import labex.feevale.br.looky.service.utils.Subject;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.SharedPreferencesUtils;
import labex.feevale.br.looky.view.adapters.DegreeAutoCompleteAdapter;

/**
 * Created by grimmjowjack on 9/10/15.
 */
public class AfterAuthorizationActivity extends AppCompatActivity
                                        implements View.OnClickListener,
                                        AdapterView.OnItemSelectedListener, Observer{

    private User user;
    private Degree selectedDegree;

    private Subject subject;

    private Button saveButton;
    private AutoCompleteTextView degreeAutoComp;
    private Spinner semesterSpinner;
    private DegreeAutoCompleteAdapter adapter;


    public AfterAuthorizationActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_authorization_activity);

        degreeAutoComp = (AutoCompleteTextView) findViewById(R.id.signinDegreeAutoComplete);
        degreeAutoComp.setThreshold(1);

        adapter = new DegreeAutoCompleteAdapter(new SubjectDao().listAllDegrees(),
                AfterAuthorizationActivity.this);
        degreeAutoComp.setAdapter(adapter);
        degreeAutoComp.setOnItemClickListener(setDegreeListener());
        setSubject(adapter);

        semesterSpinner = (Spinner) findViewById(R.id.semesterSignInSpinner);
        semesterSpinner.setOnItemSelectedListener(this);

        user = new SharedPreferencesUtils().getUser(this);
        TextView notification = (TextView) findViewById(R.id.labelNotificationLoginNome);
        notification.setText(notification.getText().toString().replace("#", user.getUsername()));

        saveButton = (Button) findViewById(R.id.finishSignInButton);
        saveButton.setOnClickListener(this);

    }

    private AdapterView.OnItemClickListener setDegreeListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedDegree = (Degree)adapterView.getItemAtPosition(position);
                user.setDegree(selectedDegree.getName());
                user.setDegreeID(selectedDegree.getId());
                degreeAutoComp.setText(selectedDegree.getName());
            }
        };
    }


    @Override
    public void onClick(View view) {
        if(validateItems()){
            user.setProfileStatus(User.COMPLETE);
            new SharedPreferencesUtils().saveUser(this, user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validateItems() {

        if(selectedDegree == null) {
            degreeAutoComp.setError(getString(R.string.notify_degree_error));
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        user.setSemester(position+1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        user.setSemester(1);
    }


    @Override
    public void update() {
        this.selectedDegree = (Degree)subject.getUpdate(this);
    }

    @Override
    public void setSubject(Subject sub) {
        this.subject = sub;
        this.subject.register(this);
    }

    @Override
    public void removeSubject(Subject sub) {
        this.subject = null;
    }
}
