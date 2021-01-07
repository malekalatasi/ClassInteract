package com.example.classinteract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class CreateAccountActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void confirmAccount(View view) {
        username = findViewById(R.id.editUsernameCreate);
        password = findViewById(R.id.editPasswordCreate);
        if(username.getText().toString().isEmpty()
                || password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Username or Password is invalid.", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences accounts = getSharedPreferences("ACCOUNTS", 0);
            SharedPreferences.Editor editor = accounts.edit();
            editor.putString("Username", username.getText().toString());
            editor.putString("Password", password.getText().toString());
            editor.commit();
            finish();
        }
    }
}
