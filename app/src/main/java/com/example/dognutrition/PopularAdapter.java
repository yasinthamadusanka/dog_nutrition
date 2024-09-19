package com.example.dognutrition;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<Popular> populars;

    public PopularAdapter(ArrayList<Popular> populars) {
        this.populars = populars;
    }

    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
        return  new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        holder.title.setText(populars.get(position).getTitle());
        holder.fee.setText(String.valueOf(populars.get(position).getFee()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(populars.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition(); // Fetch the position dynamically
                if (currentPosition != RecyclerView.NO_POSITION) { // Ensure position is valid
                    Intent intent = new Intent(holder.itemView.getContext(), ShowDetailsActivity.class);
                    intent.putExtra("popular", populars.get(currentPosition)); // Use current position
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return populars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee;
        ImageView pic;
        TextView addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            pic = itemView.findViewById(R.id.picFood);
            addBtn = itemView.findViewById(R.id.addBtn);


        }
    }
}
