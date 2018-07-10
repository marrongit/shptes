package com.example.maguilar.shptes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public Toolbar toolbar;
    ImageButton imageButtonMen;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        bindElements(view);

        imageButtonMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ManActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void bindElements(View view){
        toolbar = view.findViewById(R.id.toolbar);
        imageButtonMen = view.findViewById(R.id.imageButtonMan);
    }

}
