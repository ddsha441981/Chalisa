package com.codewithcup.chalisa.Adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codewithcup.chalisa.Activity.ContentDisplay;
import com.codewithcup.chalisa.Model.MainModel;
import com.codewithcup.chalisa.Model.SubModelClass;
import com.codewithcup.chalisa.R;


import java.util.List;

public class SubRecyclerViewAdapter extends RecyclerView.Adapter<SubRecyclerViewAdapter.SubMyHolder> {
    Context subContext;
    List<SubModelClass> subDataList;
    private static final String TAG = "SubRecyclerViewAdapter";


    public SubRecyclerViewAdapter(Context subContext, List<SubModelClass> subDataList) {
        this.subContext = subContext;
        this.subDataList = subDataList;
    }

    @NonNull
    @Override
    public SubMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater subInflater = LayoutInflater.from(subContext);
        view = subInflater.inflate(R.layout.sub_cardview_item_list,parent,false);

        return new SubMyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubMyHolder holder, final int position) {

        holder.titleSub.setText(subDataList.get(position).getName());
        //Picasso.with(subContext).load(subDataList.get(position).getImage()).into(holder.imageUrlSub);
        Glide.with(subContext).load(subDataList.get(position).getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(holder.imageUrlSub);
        holder.subCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(subContext, ContentDisplay.class);
                intent.putExtra("name",subDataList.get(position).getName());
                intent.putExtra("description",subDataList.get(position).getDescription());
                subContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return subDataList.size();
    }


    public static class SubMyHolder extends RecyclerView.ViewHolder {

        ImageView imageUrlSub;
        TextView titleSub;
        CardView subCardView;

        public SubMyHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            imageUrlSub = itemView.findViewById(R.id.sub_image_id);
            titleSub = itemView.findViewById(R.id.sub_title_id);
            subCardView = itemView.findViewById(R.id.sub_card_view);
        }

    }
}
