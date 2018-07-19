package com.example.maguilar.shptes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

/**
 * Created by maguilar on 13/07/2018.
 */

public class RecyclerShopAdapter extends RecyclerView.Adapter<RecyclerShopAdapter.ViewHolder> {

    private int layout;
    private List<ShopCar> list;
    private onItemClickListener listener;
    private Shirts shirts;
    Realm realm = Realm.getDefaultInstance();

    public RecyclerShopAdapter(int layout,List<ShopCar> list,
                               onItemClickListener listener){
        this.layout = layout;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position),listener, realm, shirts);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewSize;

        private ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCar);
            textViewTitle = itemView.findViewById(R.id.textViewTitleCar);
            textViewDesc = itemView.findViewById(R.id.textViewDescCar);
            textViewSize = itemView.findViewById(R.id.textViewSizeCar);
        }

        private void bind(final ShopCar shopCar, final onItemClickListener listener, Realm realm, Shirts shirts){
            if(shopCar.getId_subcat() == 1){
                shirts = realm.where(Shirts.class).equalTo("id",shopCar.getId_pto()).findFirst();
                imageView.setImageResource(shirts.getImage());
                textViewTitle.setText(shirts.getTitle());
                textViewDesc.setText(shirts.getDesc());
                textViewSize.setText(shirts.getSize());
            }

            final Shirts finalShirts = shirts;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(finalShirts,getAdapterPosition());
                }
            });
        }

    }

    public interface onItemClickListener{
        void onItemClick(Shirts shirts,int position);
    }
}
