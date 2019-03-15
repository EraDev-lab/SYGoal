package com.example.al_kahtani.sygoal;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.example.al_kahtani.sygoal.data.*;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AchieveRecyclerViewAdapter extends RecyclerView.Adapter<AchieveRecyclerViewAdapter.ViewHolder> {

    String name;
    int type;
    // PATCH: Because RecyclerView.Adapter in its current form doesn't natively support
    // cursors, we "wrap" a CursorAdapter that will do all teh job for us
    private CursorAdapter cursorAdapter;
    private Context mContext;
    private ViewHolder holder;
    private OnItemClickListener mListener;
    private OnLongItemClickListener mLongListener;

    public interface OnItemClickListener{
        Void onItemClick(int position, long id);
    }

    public interface OnLongItemClickListener{
        Void onLongItemClick(int position, long id);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener longListener){
        mLongListener = longListener;
    }

    public AchieveRecyclerViewAdapter(Context context, Cursor c) {
        mContext = context;
        cursorAdapter = new CursorAdapter(mContext, c, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                // Inflate the view here
                View v = LayoutInflater.from(context).inflate(R.layout.card_view_item, parent, false);
                return v;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Binding operations
                    name = cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Name));
                    type = cursor.getInt(cursor.getColumnIndex(GoalContract.Goal_Type));

                    //ToDo: get the date and make a condition to know where to go(achieve OR missed)
                    holder.goalName.setText(name);

                    if (type == 1) {
                        holder.goalType.setImageResource(R.drawable.job);
                    } else if (type == 2) {
                        holder.goalType.setImageResource(R.drawable.exercise);
                    } else if (type == 3) {
                        holder.goalType.setImageResource(R.drawable.housework);
                    } else if (type == 4) {
                        holder.goalType.setImageResource(R.drawable.social);
                    } else if (type == 5) {
                        holder.goalType.setImageResource(R.drawable.education);
                    } else if (type == 6) {
                        holder.goalType.setImageResource(R.drawable.other);
                    } else
                        holder.goalType.setImageResource(R.drawable.other);
                }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView goalName;
        public ImageView goalType;
        public CardView cardViewContainer;

        public ViewHolder(View itemView, final OnItemClickListener listener, final OnLongItemClickListener longListener) {
            super(itemView);
            goalName = itemView.findViewById(R.id.display_recycle_goal_name);
            goalType = itemView.findViewById(R.id.display_recycle_goal_type);
            cardViewContainer = itemView.findViewById(R.id.cardview);

            //          cardViewContainer.setFocusable(true);
//            cardViewContainer.setClickable(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        //ToDo: if this goalId doesn't work make on bindView if this goalId
                        long id = getItemId();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position, id);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longListener != null){
                        long longId = getItemId();
                        int longPosition = getAdapterPosition();
                        if(longPosition != RecyclerView.NO_POSITION){
                            longListener.onLongItemClick(longPosition, longId);
                        }
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Passing the binding operation to cursor loader
        cursorAdapter.getCursor().moveToPosition(position);
        cursorAdapter.bindView(holder.itemView, mContext, cursorAdapter.getCursor());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View v = cursorAdapter.newView(mContext, cursorAdapter.getCursor(), parent);
        holder = new ViewHolder(v, mListener, mLongListener);
        return holder;
    }
}
