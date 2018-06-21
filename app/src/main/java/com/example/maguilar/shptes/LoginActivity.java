package com.example.maguilar.shptes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //widgets
    public EditText editTextEmail;
    public EditText editTextPass;
    public Button buttonLogin;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindElements();

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String pass = editTextPass.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
                    validSesion(email,pass);
                } else {
                    Toast.makeText(LoginActivity.this, "Los datos son incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void bindElements(){
        editTextEmail = findViewById(R.id.edit_text_email_login);
        editTextPass = findViewById(R.id.edit_text_pass_login);
        buttonLogin = findViewById(R.id.button_login);
    }

    public void validSesion(String email, String pass){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "El email no tiene el formato adecuado", Toast.LENGTH_SHORT).show();
        } else if(pass.length() < 3){
            Toast.makeText(this, "El password debe ser de 7 a 12 caracteres", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("email",email);
            intent.putExtra("pass",pass);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            setSharedPreferences(email,pass);
        }
    }

    public void setSharedPreferences(String email,String pass){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("pass",pass);
        editor.apply();
    }

}
