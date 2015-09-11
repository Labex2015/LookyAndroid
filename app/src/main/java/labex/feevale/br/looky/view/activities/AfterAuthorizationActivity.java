package labex.feevale.br.looky.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import labex.feevale.br.looky.R;
import labex.feevale.br.looky.model.User;

/**
 * Created by grimmjowjack on 9/10/15.
 */
public class AfterAuthorizationActivity extends AppCompatActivity{

    private User user;

    public AfterAuthorizationActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_authorization_activity);
    }
}
