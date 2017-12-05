package com.soc.matthewhaynes.sqliteapp; //this is different depending on the PATH to your project. remember to CLEAN -> REBUILD when downloading

/**********************************  CUT BELOW HERE TO PASTE *****************************************************/


import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {


    DatabaseHelper socDb;  /* Declare new database object */
    InputStream inputStream; //input stream to read txt file from /res/raw
    String[] data;           //array to hold db info read from csv
    private static final String TAG = "SearchActivity"; /* used in log console to id msg */
    Spinner spinner1; //drop down menu
    Spinner spinner2; //drop down menu
    EditText class_num;
    ArrayAdapter<CharSequence> adapter; //adapter pushes strings from string.xml to drop down menu

    /* Declare Buttons */
    Button btnViewAll;

    /*********** RecyclerView stuff ******************/
    private Activity activity = SearchActivity.this;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rv_adapter;
    private GetInfoRecyclerAdapter getInfoRecyclerAdapter;
    private ArrayList<GetInfo> listInfo;
    String condition, field1, editClass;
    /*****************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       //auto-generated
        setContentView(R.layout.activity_search); //auto-generated

        /* get vars passed from MainActivity */
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        field1 = extras.getString("field1");
        condition = extras.getString("condition");
        editClass = extras.getString("editClass");


        socDb = new DatabaseHelper(this); //will call constructor of Helper class and create new db
        inputStream = getResources().openRawResource(R.raw.cecs); //open file from raw folder

        /*Cast buttons and fields*/
        btnViewAll = (Button) findViewById(R.id.button_viewSOC);
        class_num= (EditText)findViewById(R.id.editText);
         /* Call all Button Methods. If one is Clicked an 'onClickListener' (listens for buttons clicks) will activate.  */
        viewAll();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while ((csvLine= reader.readLine()) != null){
                data= csvLine.split(",");
                try{
                    Log.e("Data ", ""+data[0]+" "+data[1]+" "+data[2]+" "+data[3]+" "+data[4]+" "+data[5]+" "+data[6]+" "+data[7]+" "+data[8]+" "+data[9]+" "+data[10]+" "+data[11] +" "+data[12]+" "+data[13]);

                    /* Call isInserted() in DatabaseHelper class */
                    /* !!!!!!!!! NEED TO CHECK IF DB IS POPULATED, then run this line */
                    //socDb.insertData(data[0], data[1], data[2], data[3]);//call method with parameters

                }catch(Exception e){
                    Log.e("Problem",e.toString());
                }
            }
        } catch (IOException ex){
            throw new RuntimeException("Error in reading csv file: " +ex);
        }


        /* DROP DOWN MENU CODE HERE    */
        spinner1 = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),parent.getItemIdAtPosition(position)+ " is selected", Toast.LENGTH_LONG).show();

                switch (position){
                    case 0:
                        Toast.makeText(getBaseContext(),"CECS is selected", Toast.LENGTH_LONG).show();
                        field1 = "CECS";
                        break;
                    case 1:
                        Toast.makeText(getBaseContext(),"MATH is selected", Toast.LENGTH_LONG).show();
                        field1 = "MATH";
                        break;
                    case 2:
                        Toast.makeText(getBaseContext(),"ECE is selected", Toast.LENGTH_LONG).show();
                        field1 = "ECE";
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        adapter = ArrayAdapter.createFromResource(this,R.array.number_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(getBaseContext(),"class is equal to selected", Toast.LENGTH_LONG).show();
                        Log.d("EQUAL 2", "msg 2");
                        condition = "equalTo";
                        break;
                    case 1:
                        Toast.makeText(getBaseContext(),"class greater than selected", Toast.LENGTH_LONG).show();
                        condition = "greaterThan";
                        break;
                    case 2:
                        Toast.makeText(getBaseContext(),"class less than selected", Toast.LENGTH_LONG).show();
                        condition = "lessThan";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initViews();
        initObjects();
        filterSearch();

        /******* RecyclerView code **********************
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listInfo = new ArrayList<>();

        //dummy data
        for(int i= 0; i<10; i++) {
            GetInfo listItem = new GetInfo(
                    "heading " + (i + 1),
                    "stuff1",
                    "stuf2",
                    "stuff3"
            );
            listInfo.add(listItem);
        }
        rv_adapter = new GetInfoRecyclerAdapter(listInfo, this);
        recyclerView.setAdapter(rv_adapter);

        ******************************************/
    }
    //initialize recycler 'scroll' view
    private void initViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }
    //recycler view stuff
    private void initObjects(){
        listInfo = new ArrayList<>();
        getInfoRecyclerAdapter = new GetInfoRecyclerAdapter(listInfo, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(getInfoRecyclerAdapter);
        socDb = new DatabaseHelper(activity);

        getDataFromSQLite();
        socDb.close();
    }


    public void filterSearch(){
        int field2num, clasnum;
        ArrayList<GetInfo> newList = new ArrayList<>();

        for (GetInfo getInfo : listInfo){
            String subject = getInfo.getSubject();
            String clas  = getInfo.getClas().trim();

             field2num = Integer.parseInt(clas);
             clasnum   = Integer.parseInt(editClass);


            switch (condition){
                case "lessThan":
                    if(subject.contains(field1) && (clasnum < field2num) ){
                        newList.add(getInfo);
                    }
                    break;
                case "greaterThan":
                    if(subject.contains(field1) && (clasnum > field2num) ){
                        newList.add(getInfo);
                    }
                    break;
                case "equalTo":
                    if(subject.contains(field1) && (clasnum == field2num) ){
                        newList.add(getInfo);
                    }
                    break;
            }//end switch
        }//end for loop

        getInfoRecyclerAdapter.setFilter(newList);

    }//end function

    /**
     * This method is to fetch all user records from SQLite to put in recycler view
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                listInfo.clear();
                //socDb.getSetInfo();
                listInfo.addAll(socDb.getSetInfo());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getInfoRecyclerAdapter.notifyDataSetChanged();

            }
        }.execute();

    }










    /* Handles ViewDatabase button when pressed. Calls getAllData() in DatabaseHelper */
    public void viewAll() {
        btnViewAll.setOnClickListener(            //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {      //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                     //..notice pattern (this always here)
                    public void onClick(View v) { //declare action to be taken when button clicked

                        /* set a Cursor object equal to the result of db query getAllData() in DatabaseHelper class */
                       // Cursor res = socDb.getAllData("SOCtable");  //a Cursor object can point to a SINGLE row of the result fetched by a db query
                       // if(res.getCount() == 0) {        //if no rows are sent back display a message
                            // show message
                        //    showMessage("Error","Nothing found");
                        //    return;
                       // }

                        /* ..not sure.  Some sort of buffer that reads in database rows */
                       // StringBuffer buffer = new StringBuffer();                 //declare a buffer
                       // while (res.moveToNext()) {                                //move Cursor object 'res' to the next row
                       //     buffer.append("ID :"+ res.getString(0)+"\n");         //index 0 is first db column
                       //     buffer.append("SUBJECT :"+ res.getString(1)+"\n");       //index 1 is second db column
                       //     buffer.append("CLASS :"+ res.getString(2)+"\n");//index 2 is third db column
                       //     buffer.append("SECTION :"+ res.getString(3)+"\n\n");      //index 3 is fourth db column
                       // }

                        // call method to show all db data in a message box
                        //showMessage("Data",buffer.toString());
                        filterSearch();
                    }
                }
        );
    }

    /* method to show requested db data in message box */
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
