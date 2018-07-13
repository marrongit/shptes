package com.example.maguilar.shptes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShopCarActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewTitle;
    TextView textViewDesc;
    TextView textViewSize;
    Toolbar toolbar;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerShopAdapter recyclerShopAdapter;

    Realm realm;

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
        //deleteElements();
        //insertElements();
        List<Shirts> shirts = realm.where(Shirts.class).findAll();

        layoutManager = new LinearLayoutManager(this);
        recyclerShopAdapter = new RecyclerShopAdapter(R.layout.list_recycler_item_shop_car, shirts, new RecyclerShopAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Shirts shirts, int position) {
                Intent intent = new Intent(getApplicationContext(),ItemViewActivity.class);
                intent.putExtra("id",shirts.getId());
                intent.putExtra("subcat",shirts.getSubCategoria());
                intent.putExtra("activity","car");
                startActivity(intent);
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

    public void deleteElements(){
        realm.beginTransaction();
        realm.delete(Shirts.class);
        realm.commitTransaction();
        realm.close();
        //RealmResults<Shirts> shirts = realm.where(Shirts.class).findAll();
        //shirts.deleteAllFromRealm();
    }
}
