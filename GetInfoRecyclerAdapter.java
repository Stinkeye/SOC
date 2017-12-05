package com.soc.matthewhaynes.soc;


        import android.content.Context;
        //import android.support.v7.app.AlertController;
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

public class GetInfoRecyclerAdapter extends RecyclerView.Adapter<GetInfoRecyclerAdapter.GetInfoViewHolder>  {
    private static final String TAG = "GetInfoRecyclerAdapter";
    private ArrayList<GetInfo> listGetInfo;
    public ImageView overflow;
    private Context mContext;
    private ArrayList<GetInfo> mFilteredList;
    public Button btnAddClass;
    DatabaseHelper mDatabaseHelper;


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
        public int position;

        public String ID;
        public String subject;
        public String clas;
        public String sect;


        public GetInfoViewHolder(View view) {
            super(view);
            tvId = (TextView) view.findViewById(R.id.textViewId);
            tvSubject = (TextView) view.findViewById(R.id.textViewSubject);
            tvClas = (TextView) view.findViewById(R.id.textViewClas);
            tvSection = (TextView) view.findViewById(R.id.textViewSection);
            btnAddClass = (Button) view.findViewById(R.id.btnAddClass);

            ID = tvId.toString();
            subject = tvSubject.toString();
            clas = tvClas.toString();
            sect = tvSection.toString();
            position = getAdapterPosition();


            btnAddClass.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    position = getAdapterPosition();
                    Log.v(TAG, "MAAAAAAAAAAADE It");
                    Toast.makeText(mContext,
                            listGetInfo.get(position).getId() + "\n" +
                                    listGetInfo.get(position).getSubject() +
                                    listGetInfo.get(position).getClas(),
                            Toast.LENGTH_LONG).show();
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
        holder.tvId.setText(listGetInfo.get(position).getId());
        holder.tvSubject.setText(listGetInfo.get(position).getSubject());
        holder.tvClas.setText(listGetInfo.get(position).getClas());
        holder.tvSection.setText(listGetInfo.get(position).getSection());
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