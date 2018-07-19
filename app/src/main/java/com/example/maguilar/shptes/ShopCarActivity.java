package com.example.maguilar.shptes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShopCarActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewTitle;
    TextView textViewDesc;
    TextView textViewSize;
    Toolbar toolbar;
    Button button;
    ShopCar shopCar;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerShopAdapter recyclerShopAdapter;

    Realm realm;

    NotificationHandler notificationHandler;
    private boolean isHighImportance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        bindElements();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Mi Carrito");

        realm = Realm.getDefaultInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        //deleteElements();
        //insertElem();
        String email = sharedPreferences.getString("email","");
        Users users = realm.where(Users.class).equalTo("email",email).findFirst();
        List<ShopCar> results = realm.where(ShopCar.class).findAll();
        List<ShopCar> realmResults = realm.where(ShopCar.class).equalTo("id_user",users.getId()).findAll();
        //List<Shirts> shirts = new ArrayList<>(realm.where(Shirts.class).findAll());

        layoutManager = new LinearLayoutManager(this);
        recyclerShopAdapter = new RecyclerShopAdapter(R.layout.list_recycler_item_shop_car, realmResults, new RecyclerShopAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Shirts shirts, int position) {
                Intent intent = new Intent(getApplicationContext(),ItemViewActivity.class);
                intent.putExtra("id",shirts.getId());
                intent.putExtra("subcat",shirts.getSubCategoria());
                intent.putExtra("activity","car");
                startActivity(intent);
            }
        });


        button = findViewById(R.id.btn_notify);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification();
            }
        });


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerShopAdapter);
    }

    private void bindElements(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerViewShopCar);
        imageView = findViewById(R.id.imageViewCar);
        textViewTitle = findViewById(R.id.textViewTitleCar);
        textViewDesc = findViewById(R.id.textViewDescCar);
        textViewSize = findViewById(R.id.textViewSizeCar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void insertElements(){
        realm.beginTransaction();
        Shirts shirts = new Shirts("M","M","Azul",R.drawable.camisa_azul,"Camisa Azul Blue","Tela, verano, formal",1);
        realm.copyToRealm(shirts);
        realm.commitTransaction();
    }

    public void insertElem(){
        realm.beginTransaction();
        ShopCar shopCar = new ShopCar(1,1,1,1);
        realm.copyToRealm(shopCar);
        realm.commitTransaction();
    }

    public void deleteElements(){
        realm.beginTransaction();
        realm.delete(Shirts.class);
        realm.commitTransaction();
        realm.close();
        //RealmResults<Shirts> shirts = realm.where(Shirts.class).findAll();
        //shirts.deleteAllFromRealm();
    }

    public void notification(){
        notificationHandler = new NotificationHandler(this);

        String title = "titulo";
        String message = "message";

        Notification.Builder nb = notificationHandler.createNotification(title,message,isHighImportance);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationHandler.getNotificationManager().notify(1,nb.build());
        }
    }
}
