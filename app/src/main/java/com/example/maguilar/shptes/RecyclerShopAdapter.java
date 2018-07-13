package com.example.maguilar.shptes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by maguilar on 13/07/2018.
 */

public class RecyclerShopAdapter extends RecyclerView.Adapter<RecyclerShopAdapter.ViewHolder> {

    private int layout;
    private List<Shirts> list;
    private onItemClickListener listener;

    public RecyclerShopAdapter(int layout,List<Shirts> list, onItemClickListener listener){
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
        holder.bind(list.get(position),listener);
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

        private void bind(final Shirts shirts, final onItemClickListener listener){
            imageView.setImageResource(shirts.getImage());
            textViewTitle.setText(shirts.getTitle());
            textViewDesc.setText(shirts.getDesc());
            textViewSize.setText(shirts.getSize());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(shirts,getAdapterPosition());
                }
            });
        }

    }

    public interface onItemClickListener{
        void onItemClick(Shirts shirts,int position);
    }
}
