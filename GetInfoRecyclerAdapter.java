package com.soc.matthewhaynes.soc;


import android.content.Context;
//import android.support.v7.app.AlertController;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by matthew.haynes on 11/15/2017.
 */

public class GetInfoRecyclerAdapter extends RecyclerView.Adapter<GetInfoRecyclerAdapter.GetInfoViewHolder> {


    DatabaseHelper myDb;
    private static final String TAG = "GetInfoRecyclerAdapter";
    private ArrayList<GetInfo> listGetInfo;
    public ImageView overflow;
    private Context mContext;
    private ArrayList<GetInfo> mFilteredList;
    public Button btnAddClass;


    public GetInfoRecyclerAdapter(ArrayList<GetInfo> listGetInfo, Context mContext) {
        this.listGetInfo = listGetInfo;
        this.mContext = mContext;
        this.mFilteredList = listGetInfo;
    }

    public class GetInfoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvC1;
        public TextView tvC2;
        public TextView tvC3;
        public TextView tvC4;
        public TextView tvC5;
        public TextView tvC6;
        public TextView tvC7;
        public TextView tvC8;

        public  ImageView overflow;
        public int position;


        public String data;



        public GetInfoViewHolder(View view) {
            super(view);


            /** get TextView contents from card **/
            tvC1 = (TextView) view.findViewById(R.id.textViewId);
            tvC2 = (TextView) view.findViewById(R.id.textViewSubject);
            tvC3 = (TextView) view.findViewById(R.id.textViewClas);
            tvC4 = (TextView) view.findViewById(R.id.textViewSection);
            tvC5 = (TextView) view.findViewById(R.id.textViewTitle);
            tvC6 = (TextView) view.findViewById(R.id.textViewDay);
            tvC7 = (TextView) view.findViewById(R.id.textViewTime);
            tvC8 = (TextView) view.findViewById(R.id.textViewRoom);


            position = getAdapterPosition(); //get position of card in list

            /** Add Class Button in cardview **/
            btnAddClass = (Button) view.findViewById(R.id.btnAddClass);
            btnAddClass.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    position = getAdapterPosition();
                    myDb = new DatabaseHelper(mContext);
                    Cursor res = myDb.searchCol1("tblCatalogue", listGetInfo.get(position).getC1()); //cursor for retrieving data row by id
                    Log.i(TAG, "btnAddClass");
                    Toast.makeText(mContext,"Class Added" , Toast.LENGTH_LONG).show();


                    /** Insert Data into db personal Schedule Table **/
                    if(res.moveToFirst()) {
                        myDb.insertData("tblClass",
                                res.getString(res.getColumnIndex("id")),
                                res.getString(res.getColumnIndex("subject")),
                                res.getString(res.getColumnIndex("class")),
                                res.getString(res.getColumnIndex("section")),
                                res.getString(res.getColumnIndex("description")),
                                res.getString(res.getColumnIndex("date")),
                                res.getString(res.getColumnIndex("day")),
                                res.getString(res.getColumnIndex("time")),
                                res.getString(res.getColumnIndex("roomNum")),
                                res.getString(res.getColumnIndex("pop")),
                                res.getString(res.getColumnIndex("waitList")),
                                res.getString(res.getColumnIndex("profesor")),
                                res.getString(res.getColumnIndex("credits")),
                                res.getString(res.getColumnIndex("location")));
                    }
                    myDb.close();
                }
            });
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
        holder.tvC1.setText(listGetInfo.get(position).getC1());
        holder.tvC2.setText(listGetInfo.get(position).getC2());
        holder.tvC3.setText(listGetInfo.get(position).getC3());
        holder.tvC4.setText(listGetInfo.get(position).getC4());
        holder.tvC5.setText(listGetInfo.get(position).getC5());
        holder.tvC6.setText(listGetInfo.get(position).getC6());
        holder.tvC7.setText(listGetInfo.get(position).getC7());
        holder.tvC8.setText(listGetInfo.get(position).getC8());
    }


    @Override
    public int getItemCount() {
        return listGetInfo.size();
    }

    public void setFilter(ArrayList<GetInfo> newList){
        listGetInfo = new ArrayList<>();
        listGetInfo.addAll(newList);
        notifyDataSetChanged();
    }

}