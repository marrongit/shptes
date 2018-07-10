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

public class ManActivity extends AppCompatActivity {

    TextView expandableListTitle;

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
    List<Shirts> listPtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man);
        bindElements();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Man");

        expandibleData();
        expandibleAdapter = new ExpandibleAdapter(this,listExpandHeader,listDataChild);
        expandableListView.setAdapter(expandibleAdapter);

        listPtos = ptos();

        layoutManager = new GridLayoutManager(this,2);
        adapter = new RecyclerAdapter(listPtos, R.layout.list_recycler_item, new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Shirts ptos, int position) {
                Toast.makeText(ManActivity.this,
                        "Click en elemento "+ ptos.getTitle() + " posición " + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewClothes.setLayoutManager(layoutManager);
        recyclerViewClothes.setAdapter(adapter);

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

    public void expandibleData(){
        listExpandHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listExpandHeader.add("Ropa");

        List<String> clothes = new ArrayList<String>();
        clothes.add("Camisas");
        clothes.add("Blusas");
        clothes.add("Jeans");

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
                Toast.makeText(getApplicationContext(),
                        listExpandHeader.get(groupPosition) + " : " + listDataChild.get(listExpandHeader.get(groupPosition)).get(childPosition),Toast.LENGTH_SHORT).show();
                return false;
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
    }

    public List<Shirts> ptos(){
        return new ArrayList<Shirts>(){{
            add(new Shirts("M","M","Azul",R.drawable.camisa_azul,"Camisa Blas","Tela"));
            add(new Shirts("G","M","Rosa",R.drawable.camisa_rosa,"Camisa Shown","Lino"));
        }};
    }
}
