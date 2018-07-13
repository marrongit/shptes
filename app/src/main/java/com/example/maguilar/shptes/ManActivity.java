package com.example.maguilar.shptes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class ManActivity extends AppCompatActivity {

    TextView expandableListTitle;
    TextView textViewTitleCat;

    Toolbar toolbar;

    //ExpandibleView
    List<String> listExpandHeader;
    HashMap<String,List<String>> listDataChild;
    ExpandableListView expandableListView;
    ExpandibleAdapter expandibleAdapter;

    //RecyclerView
    RecyclerView recyclerViewClothes;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    //Ptos
    List listptos;
    List<Shirts> listShirts;
    List<Polos> listPolos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man);
        Realm realm = Realm.getDefaultInstance();
        //insert(realm);
        bindElements();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Man");

        //listShirts = realm.where(Shirts.class).findAll();
        //listptos = realm.where(Shirts.class).findAll();

        expandibleData(realm);
        expandibleAdapter = new ExpandibleAdapter(this,listExpandHeader,listDataChild);
        expandableListView.setAdapter(expandibleAdapter);

        getIntents(realm);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(ManActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Cat","1");
                startActivity(intent);
                finish();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void expandibleData(final Realm realm){
        listExpandHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listExpandHeader.add("Ropa");

        List<String> clothes = new ArrayList<String>();
        clothes.add("Camisas");
        clothes.add("Polos");
        //clothes.add("Jeans");

        listDataChild.put(listExpandHeader.get(0),clothes);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listExpandHeader.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String subcategoria = listDataChild.get(listExpandHeader.get(groupPosition)).get(childPosition);
                if(subcategoria.equals("Camisas")){
                    listShirts = realm.where(Shirts.class).findAll();
                    adapter = new RecyclerAdapter(listShirts, R.layout.list_recycler_item, new RecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Shirts shirts, int position) {
                            /*Toast.makeText(ManActivity.this,
                                    "Click en elemento "+ ptos.getTitle() + " posición " + position,
                                    Toast.LENGTH_SHORT).show();*/
                            //Realm realm = Realm.getDefaultInstance();
                            //Shirts shirts = realm.where(Shirts.class).equalTo("id",pto).findFirst();
                            Intent intent = new Intent(getApplicationContext(),ItemViewActivity.class);
                           // Bundle extras = new Bundle();
                            intent.putExtra("id",shirts.getId());
                            intent.putExtra("subcat",shirts.getSubCategoria());
                            intent.putExtra("activity","man");
                            //intent.putExtras(extras);
                            startActivity(intent);
                        }
                    });
                    layoutManager = new GridLayoutManager(getApplicationContext(),2);
                    recyclerViewClothes.setLayoutManager(layoutManager);
                    recyclerViewClothes.setAdapter(adapter);
                    expandableListView.collapseGroup(groupPosition);
                    textViewTitleCat.setText("Camisas");
                } else if(subcategoria.equals("Polos")){
                    listPolos = realm.where(Polos.class).findAll();
                    adapter = new RecyclerAdapterPolos(listPolos, R.layout.list_recycler_item, new RecyclerAdapterPolos.OnItemClickListener() {
                        @Override
                        public void onItemClick(Polos ptos, int position) {
                            Toast.makeText(ManActivity.this,
                                    "Click en elemento "+ ptos.getTitle() + " posición " + position,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    layoutManager = new GridLayoutManager(getApplicationContext(),2);
                    recyclerViewClothes.setLayoutManager(layoutManager);
                    recyclerViewClothes.setAdapter(adapter);
                    expandableListView.collapseGroup(groupPosition);
                    textViewTitleCat.setText("Polos");
                } else {
                     Toast.makeText(getApplicationContext(),
                           listExpandHeader.get(groupPosition) +
                                   " : " + listDataChild.get(listExpandHeader.get(groupPosition)).get(childPosition),
                             Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listExpandHeader.get(groupPosition) + " Collapsed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bindElements(){
        toolbar = findViewById(R.id.toolbar);
        expandableListTitle = findViewById(R.id.listTitle);
        expandableListView = findViewById(R.id.expandedList);
        recyclerViewClothes = findViewById(R.id.recyclerView);
        textViewTitleCat = findViewById(R.id.textViewTitleCat);
    }

    public void getIntents(Realm realm){
        Intent intent = getIntent();

        if(intent.hasExtra("subcat")){
            listShirts = realm.where(Shirts.class).findAll();
            adapter = new RecyclerAdapter(listShirts, R.layout.list_recycler_item, new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Shirts shirts, int position) {
                            /*Toast.makeText(ManActivity.this,
                                    "Click en elemento "+ ptos.getTitle() + " posición " + position,
                                    Toast.LENGTH_SHORT).show();*/
                    //Realm realm = Realm.getDefaultInstance();
                    //Shirts shirts = realm.where(Shirts.class).equalTo("id",pto).findFirst();
                    Intent intent = new Intent(getApplicationContext(),ItemViewActivity.class);
                    // Bundle extras = new Bundle();
                    intent.putExtra("id",shirts.getId());
                    intent.putExtra("subcat",shirts.getSubCategoria());
                    //intent.putExtras(extras);
                    startActivity(intent);
                }
            });
            layoutManager = new GridLayoutManager(getApplicationContext(),2);
            recyclerViewClothes.setLayoutManager(layoutManager);
            recyclerViewClothes.setAdapter(adapter);
            textViewTitleCat.setText("Camisas");
        }
    }

    /*public List<Polos> ptos(){
        return new ArrayList<Polos>(){{
            add(new Polos("M","M","Gris",R.drawable.polo_hombre,"Polo Ralph","Tela",2));
            //add(new Shirts("G","M","Rosa",R.drawable.camisa_rosa,"Camisa Shown","Lino",1));
        }};
    }

    private void insert(Realm realm){
        realm.beginTransaction();
        listPolos = ptos();
        realm.copyToRealm(listPolos);
        realm.commitTransaction();
    }*/
}
