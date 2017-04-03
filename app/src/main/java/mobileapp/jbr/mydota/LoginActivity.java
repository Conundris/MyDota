package mobileapp.jbr.mydota;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mobileapp.jbr.mydota.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        final AutoCompleteTextView mSteam32IDTextView = (AutoCompleteTextView) findViewById(R.id.steamID);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                intent.putExtra(EXTRA_MESSAGE, mSteam32IDTextView.getText().toString()); // can be used to send data over activites
                startActivity(intent);
            }
        });
    }
}

