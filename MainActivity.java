package com.soc.matthewhaynes.sqliteapp; //this is different depending on the PATH to your project. remember to CLEAN -> REBUILD when downloading

/**********************************  CUT BELOW HERE TO PASTE *****************************************************/

/**
 *  TUTORIAL FOUND AT
 *  http://www.codebind.com/android-tutorials-and-examples/android-sqlite-tutorial-example/
 *  https://www.youtube.com/watch?v=cp2rL3sAFmI
 */

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    /* Declare new database object */
    DatabaseHelper myDb;



    /* used in log console to id msg */
    private static final String TAG = "MAINActivity";

    /* declare buttons and Text fields. Attaches '@+id' (Button & Field ids) from /app/res/layout/activity.xml to vars in this class */
    EditText editDEPT, editCLASS, editSECTION, editTIME;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;
    Button mSearchButton;
    Button mScheduleButton;
    Spinner spinner1; //drop down menu
    Spinner spinner2; //drop down menu
    String condition, field1;
    GetInfo mGetInfo;
    ArrayAdapter<CharSequence> adapter; //adapter pushes strings from string.xml to drop down menu

    InputStream inputStream;
    String[] data;
    /* onCreate(Bundle) is where you initialize your activity.
    When Activity is started and application is not loaded,
    then both onCreate() methods will be called. But for subsequent starts of Activity ,
    the onCreate() of application will not be called */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        myDb = new DatabaseHelper(this); //will call constructor of Helper class

        /*
        inputStream = getResources().openRawResource(R.raw.cecs);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while ((csvLine= reader.readLine()) != null){
                data= csvLine.split(",");
                try{
                    Log.e("Data ", ""+data[0]+" "+data[1]+" "+data[2]+" "+data[3]+" "+data[4]+" "+data[5]+" "+data[6]+" "+data[7]+" "+data[8]+" "+data[9]+" "+data[10]+" "+data[11] +" "+data[12]+" "+data[13]);
                }catch(Exception e){
                    Log.e("Problem",e.toString());
                }
            }
        } catch (IOException ex){
            throw new RuntimeException("Error in reading csv file: " +ex);
        }
        */

         /* Casting buttons and Text fields. Attaches '@+id' (Button & Field ids) from /app/res/layout/activity.xml to vars in this class */
        btnAddData = (Button) findViewById(R.id.button_add); //attaches Button '@+id' from activity.xml (in app/res/layout) to variable in this class
        btnViewAll = (Button) findViewById(R.id.button_viewAll);
        btnDelete = (Button) findViewById(R.id.button_delete);
        editCLASS = (EditText)(findViewById(R.id.editClass));



        /* Call all Button Methods. If one is Clicked an 'onClickListener' (listens for buttons clicks) will activate.  */
        AddData();
        viewAll();

        DeleteData();

        /* User Enter Class Number */




        /*  CHANGE TO SEARCH SCREEN IF BUTTON IS PRESSED */
        mSearchButton = (Button)findViewById(R.id.search_button); // set button action
        mSearchButton.setOnClickListener(new View.OnClickListener() { //set onClickListenere to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                Log.d(TAG, "About to Start Search Activity");   //logs info to the console
                Intent intent = new Intent(MainActivity.this, SearchActivity.class); //define the 'intent' you want to accomplish 'Intent' aka new screen
                Bundle extras = new Bundle();
                extras.putString("field1",field1);
                extras.putString("condition",condition);
                extras.putString("editClass",editCLASS.getText().toString());
                intent.putExtras(extras);
                startActivity(intent); //start the intent (new screen for this purpose)
            }
        });

        /*  CHANGE TO SCHEDULE SCREEN IF BUTTON IS PRESSED */
        mScheduleButton = (Button)findViewById(R.id.schedule_button);   // set button action
        mScheduleButton.setOnClickListener(new View.OnClickListener() { //set onClickListenere to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                Log.wtf(TAG, "About to Start Schedule Activity");   //logs info to the console
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);  //define the 'intent' you want to accomplish 'Intent' aka new screen
                startActivity(intent);    //start the intent (new screen for this purpose)
            }
        });

        /* DROP DOWN MENU CODE HERE    */
        spinner1 = (Spinner) findViewById(R.id.spnSubject);
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

        spinner2 = (Spinner) findViewById(R.id.spnCondition);
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



    }//end onCreate method

    /* Handles Delete Data button when pressed. Calls deleteRows() in DatabaseHelper */
    public void DeleteData() {
        btnDelete.setOnClickListener(               //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {        //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                      //..notice pattern (this always here)

                    /* Call isInserted() in DatabaseHelper class */
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editDEPT.getText().toString());

                        /* Check if deleteRows() returns true or false regarding data deletion*/
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();  //Toast = temporary message bubble popup
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show(); //Toast = temporary message bubble popup
                    }
                }
        );
    }



    /* Handles Add Data button when pressed. Calls isInserted() in DatabaseHelper. Data will be added to database*/
    public  void AddData() {
        btnAddData.setOnClickListener(         //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {   //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                  //..notice pattern (this always here)
                    public void onClick(View v) { //declare action to be taken when button clicked

                        /* Call isInserted() in DatabaseHelper class */
                        boolean isInserted = myDb.insertData(editDEPT.getText().toString(),
                                                             editCLASS.getText().toString(),
                                                             editSECTION.getText().toString(),
                                                             editTIME.getText().toString() );//call method with parameters

                        /* Check if isInserted() returns true or false regarding data insertion */
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();  //Toast = temporary message bubble popup
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();  //Toast = temporary message bubble popup
                    }
                }
        );
    }

    /* Handles ViewDatabase button when pressed. Calls getAllData() in DatabaseHelper */
    public void viewAll() {
        btnViewAll.setOnClickListener(            //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {      //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                     //..notice pattern (this always here)
                    public void onClick(View v) { //declare action to be taken when button clicked

                        /* set a Cursor object equal to the result of db query getAllData() in DatabaseHelper class */
                        Cursor res = myDb.getAllData("SOCtable");  //a Cursor object can point to a SINGLE row of the result fetched by a db query
                        if(res.getCount() == 0) {        //if no rows are sent back display a message
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        /* ..not sure.  Some sort of buffer that reads in database rows */
                        StringBuffer buffer = new StringBuffer();                 //declare a buffer
                        while (res.moveToNext()) {                                //move Cursor object 'res' to the next row
                            buffer.append("DEPT :"+ res.getString(0)+"\n");         //index 0 is first db column
                            buffer.append("CLASS :"+ res.getString(1)+"\n");       //index 1 is second db column
                            buffer.append("SECTION :"+ res.getString(2)+"\n");//index 2 is third db column
                            buffer.append("TIME :"+ res.getString(3)+"\n\n");      //index 3 is fourth db column
                        }

                        // call method to show all db data in a message box
                        showMessage("Data",buffer.toString());
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


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */
}
