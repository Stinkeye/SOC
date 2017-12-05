package com.soc.matthewhaynes.soc;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

    DatabaseHelper myDb; /* Declare new database object */

    ArrayAdapter<CharSequence> adapter; //adapter pushes strings from string.xml to drop down menu

    InputStream inputStream; // input stream


    /** define Strings **/
    final String TAG = "MainActivity: ";
    String data[];
    String condition, field1;

    /** define buttons **/
    Button btnViewAll;
    Button btnImportDb;
    Button btnCheckSOC;
    Button btnSearchButton;
    Button btnScheduleButton;
    EditText editCLASS; /** Text Input **/




    /* Drop down menus */
    Spinner spinner1; //drop down menu
    Spinner spinner2; //drop down menu


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this); //will call constructor of Helper class

        Log.i(TAG, "In Main");   //logs info to the console


        editCLASS = (EditText)(findViewById(R.id.editClass));




        /*  CHANGE TO SEARCH SCREEN IF BUTTON IS PRESSED */
        btnSearchButton = (Button)findViewById(R.id.search_button); // set button action
        btnSearchButton.setOnClickListener(new View.OnClickListener() { //set onClickListenere to 'listen' for button clicks
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
        btnScheduleButton = (Button)findViewById(R.id.schedule_button);   // set button action
        btnScheduleButton.setOnClickListener(new View.OnClickListener() { //set onClickListenere to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                Log.wtf(TAG, "About to Start Schedule Activity");   //logs info to the console
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);  //define the 'intent' you want to accomplish 'Intent' aka new screen
                startActivity(intent);    //start the intent (new screen for this purpose)
            }
        });



        /* Import DB Button */
        inputStream = getResources().openRawResource(R.raw.cecs);
        btnImportDb = (Button) findViewById(R.id.btnImportDb);
        btnImportDb.setOnClickListener(new View.OnClickListener() { //set onClickListener to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Import DB");   //logs info to the console

                Cursor cursor;
                cursor = myDb.checkDbExists("tblCatalogue");

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                try{
                    String csvLine;
                    if( cursor.getCount() < 0){
                        cursor.close();
                        while ((csvLine= reader.readLine()) != null){
                            data= csvLine.split(",");
                            try{
                                Log.e("Data ", ""+data[0]+" "+data[1]+" "+data[2]+" "+data[3]+" "+data[4]+" "+data[5]+" "+data[6]+" "+data[7]+" "+data[8]+" "+data[9]+" "+data[10]+" "+data[11] +" "+data[12]+" "+data[13]);

                        /* Call isInserted() in DatabaseHelper class */
                        /* !!!!!!!!! NEED TO CHECK IF DB IS POPULATED, then run this line */
                        myDb.insertData("tblCatalogue", data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12], data[13]);//call method with parameters

                            }catch(Exception e){
                                Log.e("Problem",e.toString());
                                Toast.makeText(MainActivity.this,"Data NOT ENTERED",Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        cursor.close();
                        Toast.makeText(MainActivity.this,"Catalogue Already Imported",Toast.LENGTH_LONG).show();
                    }
                } catch (IOException ex){
                    throw new RuntimeException("Error in reading csv file: " +ex);
                }

            }
        });



        btnViewAll = (Button) findViewById(R.id.btnViewAll);
        btnViewAll.setOnClickListener(new View.OnClickListener() { //set onClickListener to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                Log.i(TAG, "btnViewAll");   //logs info to the console

                /* set a Cursor object equal to the result of db query getAllData() in DatabaseHelper class */


                Cursor res = myDb.getAllData("tblCatalogue");  //a Cursor object can point to a SINGLE row of the result fetched by a db query


                if(res.getCount() == 0) {        //if no rows are sent back display a message
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                        /* ..not sure.  Some sort of buffer that reads in database rows */
                StringBuffer buffer = new StringBuffer();                 //declare a buffer
                while (res.moveToNext()) {                                //move Cursor object 'res' to the next row
                    buffer.append("id :"+ res.getString(0)+"\n");         //index 0 is first db column
                    buffer.append("subject :"+ res.getString(1)+"\n");       //index 1 is second db column
                    buffer.append("class :"+ res.getString(2)+"\n");//index 2 is third db column
                    buffer.append("section :"+ res.getString(3)+"\n\n");      //index 3 is fourth db column
                }

                // call method to show all db data in a message box
                showMessage("Data",buffer.toString());

            }
        });

        btnCheckSOC = (Button) findViewById(R.id.btnCheckCatalogueExist);
        btnCheckSOC.setOnClickListener(new View.OnClickListener() { //set onClickListener to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                Log.i(TAG, "btnCheckSOC");   //logs info to the console

                Cursor cursor;
                cursor = myDb.checkDbExists("tblCatalogue");

                switch (cursor.getCount()){

                    case 0:
                        Toast.makeText(MainActivity.this,"Table DOES NOT EXIST",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        cursor = myDb.getAllData("tblCatalogue");
                        if( cursor.getCount() > 0 ){
                            Toast.makeText(MainActivity.this,"Table Exists & Populated",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Table Exists & is EMPTY",Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                myDb.close();
                cursor.close();
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





    }//end OnCreate



    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
