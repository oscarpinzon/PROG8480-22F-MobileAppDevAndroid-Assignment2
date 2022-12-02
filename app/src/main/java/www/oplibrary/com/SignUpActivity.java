package www.oplibrary.com;

import static www.oplibrary.com.Helpers.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword, edtEmail;
    Button btnSignUp, btnLoginBackToLogin;
    DBHelper dbHelper;
    Boolean lookupStatus;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    private void init() {
        edtUsername = findViewById(R.id.edtUsernameSignUp);
        edtPassword = findViewById(R.id.edtPasswordSignUp);
        edtEmail = findViewById(R.id.edtEmailIdSignUp);
        btnSignUp = findViewById(R.id.btnSignUpNew);
        btnLoginBackToLogin = findViewById(R.id.btnBackToLogin);
        dbHelper = new DBHelper(this);
        lookupStatus = false;

        btnLoginBackToLogin.setOnClickListener(v -> {
            intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnSignUp.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            String email = edtEmail.getText().toString();
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Snackbar.make(v, "Please fill all the fields", Snackbar.LENGTH_LONG).show();
                return;
            }
            if(!isValidEmail(email)) {
                Snackbar.make(v, "Please enter a valid email", Snackbar.LENGTH_LONG).show();
                return;
            }
            lookupStatus = dbHelper.checkUsernameTaken(username);
            if (lookupStatus) {
                // username taken
                Snackbar.make(v, "Username already taken", Snackbar.LENGTH_LONG).show();
            } else {
                // username not taken
                boolean result = dbHelper.insertUser(username, email, password);
                if (result) {
                    // sign up successful
                    Snackbar.make(v, "Sign up successful", Snackbar.LENGTH_LONG).show();
                    intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // sign up unsuccessful
                    Snackbar.make(v, "Sign up unsuccessful", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}