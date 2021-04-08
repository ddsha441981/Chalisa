package com.codewithcup.chalisa.Adpater;

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

import com.bumptech.glide.Glide;
import com.codewithcup.chalisa.Activity.ChalisaContainer;
import com.codewithcup.chalisa.Activity.ContentDisplay;
import com.codewithcup.chalisa.Model.MainModel;
import com.codewithcup.chalisa.Model.ModelClass;
import com.codewithcup.chalisa.R;
//import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mcontext;
    private List<ModelClass> mDataList;

    public RecyclerViewAdapter(Context mcontext, List<ModelClass> mDataList) {
        this.mcontext = mcontext;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mcontext);
        view = mInflater.inflate(R.layout.cardview_item_list,parent,false);

        MyViewHolder viewHolder = new  MyViewHolder(view);

        return viewHolder;
       // return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.titleName.setText(mDataList.get(position).getTitle());
       // holder.imageThumbnail.setImageResource(mDataList.get(position).getThumbnail());
       // Picasso.with(mcontext).load(mDataList.get(position).getThumbnail()).into(holder.imageThumbnail);
        Glide.with(mcontext).load(mDataList.get(position).getThumbnail())
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(holder.imageThumbnail);
        //next activity
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ContentDisplay.class);
                intent.putExtra("title",mDataList.get(position).getTitle());
                intent.putExtra("description",mDataList.get(position).getDescription());
                /*
                intent.putExtra("thumbnail",mDataList.get(position).getThumbnail());*/
                //intent.putExtra("title",mDataList.get(position).getTitle());
                //intent.putExtra("thumbnail",mDataList.get(position).getThumbnail());
                //intent.putExtra("name",mDataList.get(position).getName());
                //intent.putExtra("image",mDataList.get(position).getImage());
                //intent.putExtra("description",mDataList.get(position).getDescription());
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView titleName;
        ImageView imageThumbnail;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            titleName = itemView.findViewById(R.id.title_id);
            imageThumbnail = itemView.findViewById(R.id.image_id);
            cardView = itemView.findViewById(R.id.cardview_id);
        }
    }
}
