package com.example.maguilar.shptes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    //widgets
    public EditText editTextEmail;
    public EditText editTextPass;
    public Button buttonLogin;
    public SharedPreferences sharedPreferences;
    public TextView text_view_sign_out;

    //Realm
    Realm realm;
    RealmResults realmResults;
    Users realmResultsUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindElements();

        realm = Realm.getDefaultInstance();

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

        text_view_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    public void bindElements(){
        editTextEmail = findViewById(R.id.edit_text_email_login);
        editTextPass = findViewById(R.id.edit_text_pass_login);
        buttonLogin = findViewById(R.id.button_login);
        text_view_sign_out = findViewById(R.id.text_view_sign_out);
    }

    public void validSesion(String email, String pass){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "El email no tiene el formato adecuado", Toast.LENGTH_SHORT).show();
        } else if(pass.length() < 3){
            Toast.makeText(this, "El password debe ser de 7 a 12 caracteres", Toast.LENGTH_SHORT).show();
        } else {
            realmResults = realm.where(Users.class).findAll();

            if(realmResults.size() < 1){
                Toast.makeText(this, "El Usuario no esta registrado", Toast.LENGTH_SHORT).show();
            } else {
                realmResultsUser = realm.where(Users.class).equalTo("email",email).findFirst();

                if(realmResultsUser.getEmail().isEmpty()){
                    Toast.makeText(this, "El Usuario no esta registrado", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    setSharedPreferences(email,realmResultsUser);
                    Toast.makeText(this, "Bienvenido "+ realmResultsUser.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void setSharedPreferences(String email,Users realmResultsUser){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("name",realmResultsUser.getName());
        editor.apply();
    }

}
