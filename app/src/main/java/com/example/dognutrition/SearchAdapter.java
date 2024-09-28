package com.example.dognutrition;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    ArrayList<Popular> mList;

    public SearchAdapter(ArrayList<Popular> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_search, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Popular currentItem = mList.get(position);
        holder.titleTv.setText(currentItem.getTitle());
        holder.priceTv.setText(String.valueOf(currentItem.getFee()));
        holder.descriptionTv.setText(currentItem.getDescription());
        holder.ratingBar.setRating((float) currentItem.getRating());

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(mList.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.logo);

        holder.addBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(holder.itemView.getContext(), SearchDetailActivity.class);
                    intent.putExtra("popular", mList.get(currentPosition)); // Use current position
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView titleTv, priceTv, descriptionTv;
        AppCompatRatingBar ratingTv;
        TextView addBtnTv;
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.picFoodSearch);
            titleTv = itemView.findViewById(R.id.titleSearch);
            ratingTv = itemView.findViewById(R.id.ratingBarSearch);
            descriptionTv = itemView.findViewById(R.id.descriptionSearch);
            addBtnTv = itemView.findViewById(R.id.addBtnSearch);
            priceTv = itemView.findViewById(R.id.feeSearch);
            ratingBar = itemView.findViewById(R.id.ratingBarSearch);
        }
    }

    public void setFilteredList(ArrayList<Popular> filteredList) {
        this.mList = filteredList;
        notifyDataSetChanged();
    }


}
