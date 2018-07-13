package com.example.maguilar.shptes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class SignInActivity extends AppCompatActivity {

    // widgets

    private EditText editTextName;
    private EditText editTextFirstLastName;
    private EditText editTextSecondLastName;
    private EditText editTextEmail;
    private EditText editTextPass;
    private Button buttonCreateAccount;

    //SharedPreferences
    SharedPreferences sharedPreferences;

    //Realm
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bindElements();

        realm = Realm.getDefaultInstance();

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String ap_pat = editTextFirstLastName.getText().toString();
                String ap_mat = editTextSecondLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                String pass = editTextPass.getText().toString();

                if(!TextUtils.isEmpty(name) ||
                        !TextUtils.isEmpty(ap_pat) ||
                        !TextUtils.isEmpty(ap_mat) ||
                        !TextUtils.isEmpty(email) ||
                        !TextUtils.isEmpty(pass)){
                    if(validInputEmail(email)){
                        if(validInputPass(pass)){
                            createAccount(name,email,ap_pat,ap_mat,pass);
                        } else {
                            Toast.makeText(SignInActivity.this, "El password debe contener mas de 4 carácteres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, "El email no tiene formato correcto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Deben estar completos cada campo", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void saveSharedPreferences(String name,String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email",email);
                editor.apply();
    }

    private void createAccount(String name,String email,String ap_pat,String ap_mat,String pass){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        saveSharedPreferences(name,email);

        realm.beginTransaction();
        Users user = new Users(name,ap_pat,ap_mat,email,pass,0);
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    private boolean validInputPass(String pass){
        return pass.length() < 4 ? false : true;
    }

    private boolean validInputEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void bindElements(){
        editTextName = findViewById(R.id.editViewNameNew);
        editTextFirstLastName = findViewById(R.id.editViewFLNameNew);
        editTextSecondLastName = findViewById(R.id.editViewSLNameNew);
        editTextEmail = findViewById(R.id.editViewEmailNew);
        editTextPass = findViewById(R.id.editViewPassNew);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
    }
}
