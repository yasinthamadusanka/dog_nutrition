package com.example.dognutrition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<Popular> populars;
    private ManagementCart managementCart;
    private ChangeNumberItemListener changeNumberItemListener;

    public CartAdapter(ArrayList<Popular> populars, Context context, ChangeNumberItemListener changeNumberItemListener) {
        this.populars = populars;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(populars.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(populars.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((populars.get(position).getNumberInCart() * populars.get(position).getFee()) * 100) / 100));
        holder.num.setText(String.valueOf(populars.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(populars.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        // Update plus button listener
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition(); // Dynamically get current position
                if (currentPosition != RecyclerView.NO_POSITION) { // Check if position is valid
                    managementCart.plusNumberFood(populars, currentPosition, new ChangeNumberItemListener() {
                        @Override
                        public void changed() {
                            notifyDataSetChanged();
                            changeNumberItemListener.changed();
                        }
                    });
                }
            }
        });

        // Update minus button listener
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition(); // Dynamically get current position
                if (currentPosition != RecyclerView.NO_POSITION) { // Check if position is valid
                    managementCart.minusNumberFood(populars, currentPosition, new ChangeNumberItemListener() {
                        @Override
                        public void changed() {
                            notifyDataSetChanged();
                            changeNumberItemListener.changed();
                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return populars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleCart);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemtext);
            plusItem = itemView.findViewById(R.id.plusCart);
            minusItem = itemView.findViewById(R.id.minusCart);
        }
    }
}
