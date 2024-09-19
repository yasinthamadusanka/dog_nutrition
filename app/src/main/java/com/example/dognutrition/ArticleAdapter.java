package com.example.dognutrition;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private List<ArticleClass> articleClassList;

    public ArticleAdapter(Context context, List<ArticleClass> articleClassList){
        this.context = context;
        this.articleClassList = articleClassList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recImage.setImageResource(articleClassList.get(position).getDataImage());
        holder.recTitle.setText(articleClassList.get(position).getDataTitle());
        holder.recDesc.setText(articleClassList.get(position).getDataDesc());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("Image", articleClassList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Title", articleClassList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Desc", articleClassList.get(holder.getAdapterPosition()).getDataDesc());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleClassList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc;
    CardView recCard;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recTitle = itemView.findViewById(R.id.recTitle);
        recDesc = itemView.findViewById(R.id.recDesc);
        recCard = itemView.findViewById(R.id.recCard);
    }
}
