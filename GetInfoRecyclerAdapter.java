package com.soc.matthewhaynes.sqliteapp;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matthew.haynes on 11/15/2017.
 */

public class GetInfoRecyclerAdapter extends RecyclerView.Adapter<GetInfoRecyclerAdapter.GetInfoViewHolder>  {

    private ArrayList<GetInfo> listGetInfo;
    public ImageView overflow;
    private Context mContext;
    private ArrayList<GetInfo> mFilteredList;


    public GetInfoRecyclerAdapter(ArrayList<GetInfo> listGetInfo, Context mContext) {
        this.listGetInfo = listGetInfo;
        this.mContext = mContext;
        this.mFilteredList = listGetInfo;


    }

    public class GetInfoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvId;
        public TextView tvSubject;
        public TextView tvClas;
        public TextView tvSection;
        public  ImageView overflow;

        public GetInfoViewHolder(View view) {
            super(view);
            tvId = (TextView) view.findViewById(R.id.textViewId);
            tvSubject = (TextView) view.findViewById(R.id.textViewSubject);
            tvClas = (TextView) view.findViewById(R.id.textViewClas);
            tvSection = (TextView) view.findViewById(R.id.textViewSection);
        }
    }




    @Override
    public GetInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_search, parent, false);

        return new GetInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GetInfoViewHolder holder, int position) {
        holder.tvId.setText(listGetInfo.get(position).getId());
        holder.tvSubject.setText(listGetInfo.get(position).getSubject());
        holder.tvClas.setText(listGetInfo.get(position).getClas());
        holder.tvSection.setText(listGetInfo.get(position).getSection());
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }



}