package com.example.maguilar.shptes.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maguilar.shptes.R;
import com.example.maguilar.shptes.Models.Shirts;

import java.util.List;

/**
 * Created by maguilar on 10/07/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Shirts> ptos;
    private int layout;
    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(List<Shirts> ptos,int layout,OnItemClickListener onItemClickListener){
        this.ptos = ptos;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(ptos.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return ptos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Elementos UI a rellenar
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewSize;

        private ViewHolder(View itemView) {
            super(itemView);
            // Recibe la View completa. La pasa al constructor padre y enlazamos referencias UI
            // con nuestras propiedades ViewHolder declaradas justo arriba.
            imageView = itemView.findViewById(R.id.imageViewMan);
            textViewTitle = itemView.findViewById(R.id.textViewTitleMan);
            textViewDesc = itemView.findViewById(R.id.textViewDescMan);
            textViewSize = itemView.findViewById(R.id.textViewSize);
        }

        private void bind(final Shirts ptos, final OnItemClickListener listener){
            imageView.setImageResource(ptos.getImage());
            textViewTitle.setText(ptos.getTitle());
            textViewDesc.setText(ptos.getDesc());
            textViewSize.setText("Talla: "+ptos.getSize());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(ptos, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Shirts ptos, int position);
    }
}
