package com.example.maguilar.shptes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ItemViewActivity extends AppCompatActivity {

    //widgets
    ImageView imageViewPto;
    TextView textViewTitle;
    TextView textViewDesc;
    TextView textViewPrice;
    Toolbar toolbar;
    Intent intent;

    //List<Shirts> list;

    // variables
    int subcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        bindElements();

        intent = getIntent();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Producto");

        if(intent.hasExtra("id")){
            int id = intent.getIntExtra("id",0);
            subcat = intent.getIntExtra("subcat",0);
            Realm realm = Realm.getDefaultInstance();

           switch (subcat){
               case 1:
                   Shirts shirts = realm.where(Shirts.class).equalTo("subCategoria",subcat).findFirst();
                   textViewTitle.setText("id "+id+" "+"subcategoria "+subcat);
                   textViewDesc.setText(shirts.getDesc());
                   imageViewPto.setImageResource(shirts.getImage());
                   break;
           }
        }

    }

    private void bindElements(){
        toolbar = findViewById(R.id.toolbar);
        imageViewPto = findViewById(R.id.item_view_image);
        textViewTitle = findViewById(R.id.textViewTitleItem);
        textViewDesc = findViewById(R.id.textViewDescItem);
        textViewPrice = findViewById(R.id.textViewPriceItem);
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
