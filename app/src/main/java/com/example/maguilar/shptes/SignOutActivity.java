package com.example.maguilar.shptes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignOutActivity extends Activity {

    // widgets

    private EditText editTextName;
    private EditText editTextFirstLastName;
    private EditText editTextSecondLastName;
    private EditText editTextEmail;
    private EditText editTextPass;
    private Button buttonCreateAccount;

    //SharedPreferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        bindElements();

        sharedPreferences = getSharedPreferences("preferences",MODE_PRIVATE);

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
                            createAccount(name,email);
                        } else {
                            Toast.makeText(SignOutActivity.this, "El password debe contener mas de 4 carácteres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignOutActivity.this, "El email no tiene formato correcto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignOutActivity.this, "Deben estar completos cada campo", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveSharedPreferences(String name,String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name",name);
                editor.putString("email",email);
                editor.apply();
    }

    private void createAccount(String name,String email){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        saveSharedPreferences(name,email);
    }

    private boolean validInputPass(String pass){
        return pass.length() < 4;
    }

    private boolean validInputEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void bindElements(){
        editTextName = findViewById(R.id.editViewNameNew);
        editTextName = findViewById(R.id.editViewFLNameNew);
        editTextName = findViewById(R.id.editViewSLNameNew);
        editTextName = findViewById(R.id.editViewEmailNew);
        editTextName = findViewById(R.id.editViewPassNew);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
    }
}
