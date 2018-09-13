package com.szakdolgozat.administration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.szakdolgozat.log.CustomLogger;
import com.szakdolgozat.log.Logging;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements RegisterCallback {

    private static final CustomLogger log = Logging.getLogger(LoginActivity.class);

    private EditText nameField;
    private EditText passwordField;
    private ProgressBar progressBar;
    private LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Button loginButton = findViewById(R.id.LoginButton);
        nameField = findViewById(R.id.NameField);
        passwordField = findViewById(R.id.PasswordField);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name = nameField.getText().toString();
                String pass = passwordField.getText().toString();
                log.info("name: " + name + " pass: " + pass);
                if (loginTask != null) {
                    loginTask.cancel(true);
                }
                loginTask = new LoginTask(name, pass);
                loginTask.execute();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (loginTask != null) {
            loginTask.cancel(true);
        }
    }

    @Override
    public void onRegister(String s) {
        log.info("end: " + s);
    }

    private class LoginTask extends AsyncTask<Void, Void, List<String>> {

        private String name;
        private String password;
        boolean err = false;

        private LoginTask(String name, String pass) {
            this.name = name;
            this.password = pass;
        }

        private void callReg(RegisterCallback registerCallback, String message) {
            registerCallback.onRegister(message);
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> res = new ArrayList<>();
            // TODO: 2018. 09. 12. elküldeni a szerónak a logint
            err = true;
            return res;
        }

        @Override
        protected void onPostExecute(List<String> res) {
            progressBar.setVisibility(View.GONE);
            if (err) {
                Toast.makeText(LoginActivity.this, "Unable to login!", Toast.LENGTH_SHORT).show();
            } else if (!res.isEmpty()) {
                callReg(LoginActivity.this, res.get(0));
            } else {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
