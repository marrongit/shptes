package com.example.maguilar.shptes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ItemViewActivity extends AppCompatActivity {

    //widgets
    ImageView imageViewPto;
    TextView textViewTitle;
    TextView textViewDesc;
    TextView textViewPrice;
    ImageView imageViewShopCar;
    Toolbar toolbar;
    Intent intent;
    Realm realm = Realm.getDefaultInstance();
    MenuItem menuItem;

    //List<Shirts> list;

    // variables
    int subcat,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        bindElements();
        final SharedPreferences sharedPreferences = getSharedPreferences("preferences",Context.MODE_PRIVATE);

        intent = getIntent();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Producto");

        if(intent.hasExtra("id")){
            id = intent.getIntExtra("id",0);
            subcat = intent.getIntExtra("subcat",0);
            //final Realm realm = Realm.getDefaultInstance();

           switch (subcat){
               case 1:
                   final Shirts shirts = realm.where(Shirts.class).equalTo("subCategoria",subcat).findFirst();
                   textViewTitle.setText("id "+id+" "+"subcategoria "+subcat);
                   textViewDesc.setText(shirts.getDesc());
                   imageViewPto.setImageResource(shirts.getImage());
                   break;
           }
        }

        imageViewShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                String email = sharedPreferences.getString("email","");
                Users users = realm.where(Users.class).equalTo("email",email).findFirst();
                ShopCar shopCar = new ShopCar(users.getId(),id,1,subcat);
                realm.copyToRealm(shopCar);
                RealmResults<ShopCar> countShopCar = realm.where(ShopCar.class).findAll();
                int count = countShopCar.size();
                Toast.makeText(ItemViewActivity.this,
                        "Se han guardado "+count+" en tu carrito", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void bindElements(){
        toolbar = findViewById(R.id.toolbar);
        imageViewPto = findViewById(R.id.item_view_image);
        textViewTitle = findViewById(R.id.textViewTitleItem);
        textViewDesc = findViewById(R.id.textViewDescItem);
        textViewPrice = findViewById(R.id.textViewPriceItem);
        imageViewShopCar = findViewById(R.id.button_shop_item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                if(intent.hasExtra("activity")){
                    String activity = intent.getStringExtra("activity");
                    if(activity.equals("man")){
                        Intent intent = new Intent(this,ManActivity.class);
                        intent.putExtra("subcat",subcat);
                        startActivity(intent);
                    } else if(activity.equals("car")){
                        Intent intent = new Intent(this,ShopCarActivity.class);
                        intent.putExtra("subcat",subcat);
                        startActivity(intent);
                    }
                }

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
