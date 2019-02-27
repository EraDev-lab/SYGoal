package com.example.al_kahtani.sygoal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{



    private Context mContex ;
    private List<GoalVariables> mData;

    public RecyclerViewAdapter(Context mContex, List<GoalVariables> mData) {
        this.mContex = mContex;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view;
        LayoutInflater mInflater =LayoutInflater.from(mContex);
        view = mInflater.inflate(R.layout.card_view_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_goal_title.setText(mData.get(position).getTitle());
        holder.img_goal_thumbnail.setImageResource(mData.get(position).getThumbnail());

        //set click listener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContex,DisplayTaskScreen.class);

                // start the activity
                mContex.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_goal_title;
        ImageView img_goal_thumbnail;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_goal_title = (TextView)itemView.findViewById(R.id.goal_textview);
            img_goal_thumbnail=(ImageView)itemView.findViewById(R.id.goal_img);
            cardView =(CardView) itemView.findViewById(R.id.cardview);


        }
    }



}
