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


/**
 * we add a constructor to the RecyclerViewAdapter so that it has a handle to the data that the RecyclerView displays.
 * As our data is in the form of a List of Goal objects
     */
    private Context mContex ;
    private List<GoalClass> mData;

    public RecyclerViewAdapter(Context mContex, List<GoalClass> mData) {
        this.mContex = mContex;
        this.mData = mData;
    }
/*
 This method is called when the custom ViewHolder needs to be initialized.
 We specify the layout that each item of the RecyclerView should use.
  This is done by inflating the layout using LayoutInflater,
  passing the output to the constructor of the custom ViewHolder.
 */
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater =LayoutInflater.from(mContex);
        view = mInflater.inflate(R.layout.card_view_item, parent,false);
        return new MyViewHolder(view);
    }
/*
  here's where we have to set the values of the name of the goal and photo fields of the CardView.
 */
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
/*
*This should return the number of items present in the data. As our data is in the form of a List,
* we only need to call the size method on the List object
 */
    @Override
    public int getItemCount() {

        return mData.size();
    }

   /**w
     * we already defined the XML layout for a CardView that represents a person. We are going to reuse that layout now.
    * Inside the constructor of our custom ViewHolder, initialize the views that belong to the items of our RecyclerVie.
     */
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
