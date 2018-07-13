package com.example.maguilar.shptes;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    //widgets
    ImageView imageProfile;
    TextView textViewName;
    TextView textViewEmail;
    TextView textViewPass;
    //sharedPreferences
    SharedPreferences sharedPreferences;
    //Realm
    Realm realm;
    //variables
    String email,name,pass;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        bindElements(view);

        realm = Realm.getDefaultInstance();
        sharedPreferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");

        if(!email.equals("")){
            Users users = realm.where(Users.class).equalTo("email",email).findFirst();
            int image = users.getImage();

            if(image != 0){
                imageProfile.setImageResource(users.getImage());
            } else {
                imageProfile.setImageResource(R.drawable.ic_profile);
            }

            textViewName.setText(users.getName()+" "+users.getF_last_name()+" "+users.getS_last_name());
            textViewEmail.setText(users.getEmail());
        }

        return view;
    }

    private void bindElements(View view){
        imageProfile = view.findViewById(R.id.imageProfile);
        textViewName = view.findViewById(R.id.textViewNameProfile1);
        textViewEmail = view.findViewById(R.id.textViewEmailProfile1);
    }

}
