package com.example.maguilar.shptes;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    TextView textViewNameND;
    ImageButton imageButtonMen;
    ImageView imageViewProfile;

    public SharedPreferences sharedPreferences;
    public DrawerLayout drawerLayout;
    NavigationView navigationView;
    public Toolbar toolbar;

    List<Users> list;

    //Bundle bundle = new Bundle();

    //VARIABLES
    String Cat,email;

    //Realm
    Realm realm;

    //Intent
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        final Intent intent = getIntent();
        bindElements();
        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContentDrawer,fragment).commit();
        navigationView.setCheckedItem(R.id.home_option_drawer);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = sharedPreferences.getString("email","");
        Users users = realm.where(Users.class).equalTo("email",email).findFirst();

        if(users == null){
            //Toast.makeText(this, "No hay valor", Toast.LENGTH_SHORT).show();
            signOut();
        } else {
            textViewNameND.setText(email);
        }

        if(intent.hasExtra("Cat")){
             Cat = intent.getStringExtra("Cat");
            if(Cat.equals("1")){
                Fragment newFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContentDrawer,newFragment).commit();
                getSupportActionBar().setTitle(getTitle());
                navigationView.setCheckedItem(R.id.home_option_drawer);
            }
        }

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContentDrawer,newFragment).commit();
                getSupportActionBar().setTitle(getTitle());
                navigationView.setCheckedItem(R.id.profile_option_drawer);
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(MainActivity.this, "Opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(MainActivity.this, "Closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.home_option_drawer:
                        fragment = new HomeFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.profile_option_drawer:
                        fragment = new ProfileFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.sign_out:
                        signOut();
                        break;
                }

                if(fragmentTransaction){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameContentDrawer,fragment).commit();
                    item.setChecked(true);
                    getSupportActionBar().setTitle(item.getTitle());
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.shopCar:
                intent = new Intent(this,ShopCarActivity.class);
                startActivity(intent);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void signOut(){
        sharedPreferences.edit().clear().commit();
        intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void bindElements(){
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        textViewNameND = findViewById(R.id.textViewNameND);
        imageButtonMen = findViewById(R.id.imageButtonMan);
        imageViewProfile = findViewById(R.id.imageProfileDrawer);
    }
}
