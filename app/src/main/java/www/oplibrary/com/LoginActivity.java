package www.oplibrary.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword, edtEmail;
    Button btnLogin;
    DBHelper dbHelper;
    Boolean lookupStatus;
    Intent intent;
    SharedPreferences sharedPref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        edtUsername = findViewById(R.id.edtUid);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmailId);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DBHelper(this);
        lookupStatus = false;
        sharedPref1 = getSharedPreferences("login_details", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            String email = edtEmail.getText().toString();
            lookupStatus = dbHelper.checkUser(username, password, email);
            if (lookupStatus) {
                // login successful
                intent = new Intent(LoginActivity.this, HomeActivity.class);
                SharedPreferences.Editor editor1 = sharedPref1.edit();
                editor1.putString("USER_ID", edtUsername.getText().toString().trim());
                editor1.putString("EMAIL_ID", edtEmail.getText().toString().trim());
                editor1.apply();
                startActivity(intent);
            } else {
                Snackbar.make(v, "Invalid username or password", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
